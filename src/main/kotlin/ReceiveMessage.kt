package agents

import jade.core.behaviours.CyclicBehaviour
import jade.lang.acl.ACLMessage

class ReceiveMessage(private val agent: DefaultAgent): CyclicBehaviour(agent) {
    override fun action() {
        val message: ACLMessage? = agent.receive()
        message?.let {
            val (id, value) = message.content.split(":").map { it.toInt() }
            println("Receive: id=$id, value=$value")
            if (id !in agent.collectedData) {
                println("Add a new value: $id, $value")
                agent.collectedData[id] = value
                agent.messagesToSend.push(message.content)
            }
            block()
        }
    }
}
