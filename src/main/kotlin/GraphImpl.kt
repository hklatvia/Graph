class GraphImpl(edges: List<Triple<Int, Int, Int>>) : Graph {
    var adjacencyMap = mutableListOf<MutableList<Int>>()

    init {
        val numVertices = edges.flatMap {
            listOf(it.first, it.second)
        }.maxOrNull()?.plus(1) ?: 0
        adjacencyMap = MutableList(numVertices) { MutableList(numVertices) { 0 } }
        edges.forEach() {
            adjacencyMap[it.first][it.second] = it.third
            adjacencyMap[it.second][it.first] = it.third
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
        val combinedMap = this.adjacencyMap + other.adjacencyMap
        println(combinedMap.toMutableList())
        val newGraph = GraphImpl()
        newGraph.adjacencyMap = combinedMap.toMutableList()
        return newGraph
    }

    override fun addEdge(vertex1: Int, vertex2: Int, weight: Int) {
        expandAdjacencyMap(vertex1, vertex2)
        adjacencyMap[vertex1][vertex2] = weight
        adjacencyMap[vertex2][vertex1] = weight
    }

    override fun removeEdge(vertex1: Int, vertex2: Int) {
        adjacencyMap[vertex1][vertex2] = 0
        adjacencyMap[vertex2][vertex1] = 0
    }

    override fun updateEdge(vertex1: Int, vertex2: Int, newWeight: Int) {
        adjacencyMap[vertex1][vertex2] = newWeight
        adjacencyMap[vertex2][vertex1] = newWeight
    }

    override fun findShortestWay(source: Int): List<Int> {
        val numVertices = adjacencyMap.size
        val distances = MutableList(numVertices) { Int.MAX_VALUE }
        val visited = BooleanArray(numVertices)

        distances[source] = 0

        for (i in 0 until numVertices - 1) {
            val minVertex = minDistance(distances, visited)
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

    private fun minDistance(distances: List<Int>, visited: BooleanArray): Int {
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

    private fun expandAdjacencyMap(vertex1: Int, vertex2: Int) {
        val maxVertex = maxOf(vertex1, vertex2)
        val expandedAdjacencyMap = MutableList(maxVertex + 1) { MutableList(maxVertex + 1) { 0 } }
        if (maxVertex < adjacencyMap.lastIndex) {
            return
        } else {
            adjacencyMap.forEachIndexed { i, row ->
                row.forEachIndexed { j, value ->
                    expandedAdjacencyMap[i][j] = value
                }
            }
            adjacencyMap = expandedAdjacencyMap
        }
    }
}


