package agents

import jade.core.behaviours.CyclicBehaviour
import jade.lang.acl.ACLMessage

class ReceiveMessage(private val agent: DefaultAgent): CyclicBehaviour(agent) {
    override fun action() {
        val message: ACLMessage? = agent.receive()
        message?.let {
            agent.prn("Received message: '${message.content}' from ${message.sender.localName}")
            val (rawMsgType, rawValue) = message.content.split(":")
            val value = rawValue.toInt()
            val msgType = MessageType.valueOf(rawMsgType)
            if (msgType == MessageType.Request) {
                agent.parent?.let {
                    agent.prn("I'm already in progress, Response:0 to ${message.sender.localName}")
                    agent.sendMessage("Response:0", message.sender.localName)
                    block()
                    return
                }
                agent.parent = message.sender.localName
                agent.linkedAgents.remove(message.sender.localName)
                println("Parent of ${agent.localName} is ${message.sender.localName}")
                agent.nextStep()
            } else {
                agent.prn("Receive a new value $value from ${message.sender.localName}")
                agent.result += value
                agent.nextStep()
            }
            block()
        }
    }
}
