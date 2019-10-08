package agents

import jade.core.AID
import jade.core.behaviours.TickerBehaviour
import jade.lang.acl.ACLMessage


class GettingMessageException(override val message: String) : Exception(message)


class SendMessage(private val agent: DefaultAgent, period: Long) : TickerBehaviour(agent, period) {
    override fun onTick() {
        println("Collection state: ${agent.collectedData.size.toFloat() / AGENTS_COUNT}, mts: ${agent.messagesToSend}")
        println(
            "Agent '${agent.localName}'," +
                    " collection: ${agent.collectedData.size.toFloat() / AGENTS_COUNT}," +
                    " mts: ${agent.messagesToSend}"
        )
        if (agent.messagesToSend.isNotEmpty()) {
            val message = agent.messagesToSend.pop()
            agent.linkedAgents.forEach { linkedAgent ->
                val msg = ACLMessage(ACLMessage.INFORM)
                val dest = AID(linkedAgent, false)
                msg.content = message
                msg.addReceiver(dest)
                agent.send(msg)
            }
        } else if (agent.collectedData.size == AGENTS_COUNT) {
            stop()
            println("Agent ${agent.localName} has been stopped")
            println("Result: ${agent.result}")
            val minId = agent.collectedData.minBy { (id, _) -> id }?.key
                ?: throw GettingMessageException("Inappropriate state")
            if (minId == agent.localName.toInt()) {
                println("Indexes: $minId, ${agent.localName.toInt()}")
                println("Agent ${agent.localName} is a leader. Final result: ${agent.result}")
            }
        }
    }
}
