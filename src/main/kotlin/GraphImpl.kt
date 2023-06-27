class GraphImpl(private val edges: List<Triple<Int, Int, Int>>) : Graph {
    var adjacencyMap = buildAdjacencyMap().toMutableList()

    fun printGraph() {
        adjacencyMap.forEach() {
            val rowString = it.joinToString(" ")
            println(rowString)
        }
    }

    fun printDijkstraResult(distances: MutableList<Int>, source: Int) {
        println("Dijkstra's Shortest Paths:")
        for (v in distances.indices) {
            println("From point $source to point $v the shortest way :  ${distances[v]}")
        }
    }

    operator fun plus(other: GraphImpl): GraphImpl {
        val combinedEdges = (this.edges + other.edges).distinct()
        val newGraph = GraphImpl(combinedEdges)
        newGraph.adjacencyMap = newGraph.buildAdjacencyMap().toMutableList()
        return newGraph
    }

    override fun addEdge(vertex1: Int, vertex2: Int, weight: Int) {
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
        val numVertices = buildAdjacencyMap().size
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

    private fun minDistance(distances: MutableList<Int>, visited: BooleanArray): Int {
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

    private fun buildAdjacencyMap(): List<MutableList<Int>> {
        val numVertices = edges.flatMap {
            listOf(it.first, it.second)
        }.maxOrNull()?.plus(1) ?: 0
        val newAdjacencyMap = MutableList(numVertices) { MutableList(numVertices) { 0 } }
        edges.forEach() {
            newAdjacencyMap[it.first][it.second] = it.third
            newAdjacencyMap[it.second][it.first] = it.third
        }
        return newAdjacencyMap
    }
}


