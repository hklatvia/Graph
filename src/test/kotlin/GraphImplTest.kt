import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GraphTests {
    private val edges = listOf(
        Triple(0, 1, 2),
        Triple(1, 2, 3),
        Triple(2, 3, 4)
    )
    private val edges1 = listOf(
        Triple(0, 1, 5),
        Triple(1, 2, 6),
        Triple(2, 3, 7)
    )

    @Test
    fun addEdge() {
        val graph = GraphImpl(edges)
        val expectedResult = listOf(
            listOf(0, 2, 0, 0),
            listOf(2, 0, 5, 0),
            listOf(0, 5, 0, 4),
            listOf(0, 0, 4, 0),
        )
        graph.addEdge(1, 2, 5)
        val result = graph.adjacencyMap
        assertEquals(expectedResult, result)
    }

    @Test
    fun changeEdge() {
        val graph = GraphImpl(edges)
        val expectedResult = listOf(
            listOf(0, 5, 0, 0),
            listOf(5, 0, 3, 0),
            listOf(0, 3, 0, 4),
            listOf(0, 0, 4, 0),
        )
        graph.updateEdge(0, 1, 5)
        val result = graph.adjacencyMap
        assertEquals(expectedResult, result)
    }

    @Test
    fun removeEdge() {
        val graph = GraphImpl(edges)
        val expectedResult = listOf(
            listOf(0, 0, 0, 0),
            listOf(0, 0, 3, 0),
            listOf(0, 3, 0, 4),
            listOf(0, 0, 4, 0),
        )
        graph.removeEdge(0, 1)
        val result = graph.adjacencyMap
        assertEquals(expectedResult, result)
    }

    @Test
    fun findShortestWay() {
        val graph = GraphImpl(edges)
        val expectedResult = listOf(0, 2, 5, 9)
        val result = graph.findShortestWay(0)
        assertEquals(expectedResult, result)
    }

    @Test
    fun plus() {
        val expectedResult = listOf(
            listOf(0, 5, 0, 0),
            listOf(5, 0, 6, 0),
            listOf(0, 6, 0, 7),
            listOf(0, 0, 7, 0),
        )
        val graph = GraphImpl(edges)
        val graph1 = GraphImpl(edges1)
        val result = (graph + graph1).adjacencyMap
        assertEquals(expectedResult, result)
    }

}