interface Graph {
    fun findShortestWay(source: Int): List<Int>
    fun addEdge(vertex1: Int, vertex2: Int, weight: Int)
    fun removeEdge(vertex1: Int, vertex2: Int)
    fun updateEdge(vertex1: Int, vertex2: Int, newWeight: Int)
}