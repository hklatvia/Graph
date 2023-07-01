class GraphImpl(edges: List<Triple<String, String, Int>>) : Graph {
    private var adjacencyMap = mutableListOf<MutableList<Int>>()
    private var vertexMap = mutableMapOf<String, Int>()

    init {
        val vertices = edges.flatMap { listOf(it.first, it.second) }.distinct()

        vertices.forEachIndexed { index, value ->
            vertexMap[value] = index
        }
        adjacencyMap = MutableList(vertices.size) { MutableList(vertices.size) { 0 } }

        edges.forEach { edge ->
            val firstVertex = vertexMap[edge.first] ?: 0
            val secondVertex = vertexMap[edge.second] ?: 0
            adjacencyMap[firstVertex][secondVertex] = edge.third
            adjacencyMap[secondVertex][firstVertex] = edge.third
        }
    }

    constructor() : this(emptyList())

    fun printGraph() {
        adjacencyMap.forEach() {
            val rowString = it.joinToString(" ")
            println(rowString)
        }
    }

    operator fun plus(other: GraphImpl): GraphImpl {
        val combinedEdges = (this.toEdges() + other.toEdges()).distinct()
        return GraphImpl(combinedEdges)
    }

    override fun addEdge(vertex1: String, vertex2: String, weight: Int) {
        val indexOfVertex1 = vertexMap[vertex1] ?: vertexMap.size
        vertexMap[vertex1] = indexOfVertex1
        val indexOfVertex2 = vertexMap[vertex2] ?: vertexMap.size

        vertexMap[vertex2] = indexOfVertex2
        expandAdjacencyMap(vertexMap.size)
        adjacencyMap[indexOfVertex1][indexOfVertex2] = weight
        adjacencyMap[indexOfVertex2][indexOfVertex1] = weight
    }

    override fun removeEdge(vertex1: String, vertex2: String) {
        val indexOfVertex1 = vertexMap[vertex1] ?: throw IllegalArgumentException("The vertex is not found")
        val indexOfVertex2 = vertexMap[vertex2] ?: throw IllegalArgumentException("The vertex is not found")

        adjacencyMap[indexOfVertex1][indexOfVertex2] = 0
        adjacencyMap[indexOfVertex2][indexOfVertex1] = 0
        vertexMap.remove(vertex1)
        vertexMap.remove(vertex2)
    }

    override fun updateEdge(vertex1: String, vertex2: String, newWeight: Int) {
        val indexOfVertex1 = vertexMap[vertex1] ?: throw IllegalArgumentException("The vertex is not found")
        val indexOfVertex2 = vertexMap[vertex2] ?: throw IllegalArgumentException("The vertex is not found")
        adjacencyMap[indexOfVertex1][indexOfVertex2] = newWeight
        adjacencyMap[indexOfVertex2][indexOfVertex1] = newWeight
    }

    override fun findShortestWay(source: String): List<Int> {
        val numVertices = adjacencyMap.size
        val distances = MutableList(numVertices) { Int.MAX_VALUE }
        val visited = BooleanArray(numVertices)
        val sourceIndex = vertexMap[source] ?: return emptyList()

        distances[sourceIndex] = 0

        for (i in 0 until numVertices - 1) {
            val minVertex = findMinDistance(distances, visited)
            visited[minVertex] = true

            for (v in 0 until numVertices) {
                if (!visited[v] && adjacencyMap[minVertex][v] != 0 && distances[minVertex] != Int.MAX_VALUE
                    && distances[minVertex] + adjacencyMap[minVertex][v] < distances[v]
                ) {
                    distances[v] = distances[minVertex] + adjacencyMap[minVertex][v]
                }
            }
        }
        return distances
    }

    private fun findMinDistance(distances: List<Int>, visited: BooleanArray): Int {
        var min = Int.MAX_VALUE
        var minIndex = -1

        for (v in distances.indices) {
            if (!visited[v] && distances[v] <= min) {
                min = distances[v]
                minIndex = v
            }
        }
        return minIndex
    }

    private fun expandAdjacencyMap(vertexMapSize: Int) {
        val expandedAdjacencyMap = MutableList(vertexMapSize) { MutableList(vertexMapSize) { 0 } }
        adjacencyMap.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                expandedAdjacencyMap[i][j] = value
            }
        }
        adjacencyMap = expandedAdjacencyMap
    }

    fun toEdges(): List<Triple<String, String, Int>> {
        val edges = mutableSetOf<Triple<String, String, Int>>()

        adjacencyMap.forEachIndexed { i, row ->
            row.forEachIndexed { j, weight ->
                if (weight != 0) {
                    val vertex1 = vertexMap.entries.find {
                        it.value == i
                    }?.key
                    val vertex2 = vertexMap.entries.find {
                        it.value == j
                    }?.key
                    if (vertex1 != null && vertex2 != null) {
                        edges.add(Triple(vertex1, vertex2, weight))
                    }
                }
            }
        }
        val uniqueEdges = mutableListOf<Triple<String, String, Int>>()
        val comparator = edgeComparator()

        edges.forEach { edge ->
            if (!uniqueEdges.any { comparator.compare(it, edge) == 0 }) {
                uniqueEdges.add(edge)
            }
        }
        return uniqueEdges
    }


    private fun edgeComparator(): Comparator<Triple<String, String, Int>> {
        return Comparator { edge1, edge2 ->
            if ((edge1.first == edge2.second && edge1.second == edge2.first) ||
                (edge1.first == edge2.first && edge1.second == edge2.second)
            ) {
                0
            } else {
                edge1.hashCode().compareTo(edge2.hashCode())
            }
        }
    }
}


