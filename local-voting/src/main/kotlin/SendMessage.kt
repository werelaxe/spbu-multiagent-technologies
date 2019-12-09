package agents

import jade.core.behaviours.OneShotBehaviour


class SendMessage(private val agent: DefaultAgent) : OneShotBehaviour() {
    override fun action() {
        for (linkedAgent in agent.linkedAgents) {
//            agent.sendMessage("Hello, I'm ${agent.localName}!", linkedAgent)
            agent.sendMessage(agent.result.toString(), linkedAgent)
        }
    }
}
