package agents

import jade.core.Agent
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class DefaultAgent: Agent() {
    val linkedAgents = mutableSetOf<String>()
    val messagesToSend = ArrayDeque<String>()
    val collectedData = hashMapOf<Int, Int>()
    val result: Float
        get() = collectedData.map { it.value }.sum().toFloat() / collectedData.size
    private val value = Random.nextInt(0, 100)
    private val message: String
        get() = "$localName:$value"

    override fun setup() {
        val id = aid.localName.toInt()
        linkedAgents.add((id % AGENTS_COUNT + 1).toString())
        for (i in 0..3) {
            val linkedId = Random.nextInt(AGENTS_COUNT) + 1
            if (linkedId != id) {
                linkedAgents.add(linkedId.toString())
            }
        }
        messagesToSend.push(message)
        collectedData[id] = value
        println("Agent with name='$localName' has been registered: $localName: $linkedAgents")
        addBehaviour(SendMessage(this, TimeUnit.MILLISECONDS.toMillis(100)))
        addBehaviour(ReceiveMessage(this))
    }
}
