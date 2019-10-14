package agents

import jade.core.behaviours.OneShotBehaviour


class GettingMessageException(override val message: String) : Exception(message)


class SendMessage(private val agent: DefaultAgent) : OneShotBehaviour() {
    override fun action() {
        if (agent.localName == LEADER_ID) {
            agent.parent = agent.localName
            agent.linkedAgents.forEach {linkedAgent ->
                agent.sendMessage("Request:0", linkedAgent)
            }
        }
    }
}
