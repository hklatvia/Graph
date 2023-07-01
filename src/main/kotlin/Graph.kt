interface Graph {
    fun findShortestWay(source: String): List<Int>
    fun addEdge(vertex1: String, vertex2: String, weight: Int)
    fun removeEdge(vertex1: String, vertex2: String)
    fun updateEdge(vertex1: String, vertex2: String, newWeight: Int)
}