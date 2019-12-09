package agents

import jade.core.AID
import jade.core.Agent
import jade.lang.acl.ACLMessage


fun Agent.prn(message: String) {
    println("$localName: $message")
}


fun Agent.sendMessage(message: String, receiverId: String) {
    val msg = ACLMessage(ACLMessage.INFORM)
    val dest = AID(receiverId, false)
    msg.content = message
    msg.addReceiver(dest)
    send(msg)
}
