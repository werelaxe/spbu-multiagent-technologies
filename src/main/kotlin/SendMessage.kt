package agents

import jade.core.behaviours.OneShotBehaviour


class SendMessage(private val agent: DefaultAgent) : OneShotBehaviour() {
    override fun action() {
        if (agent.localName == LEADER_ID) {
            agent.parent = agent.localName
            agent.nextStep()
        }
    }
}
