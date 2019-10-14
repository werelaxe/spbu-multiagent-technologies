package agents

import jade.core.Profile
import jade.core.ProfileImpl
import jade.core.Runtime
import java.lang.Exception
import kotlin.random.Random
import kotlin.system.exitProcess


const val AGENTS_COUNT = 5
const val LEADER_ID = "1"

enum class MessageType {
    Request, Response
}


val linkedAgentsById = mutableMapOf<String, MutableSet<String>>()


fun main() {
    for (i in 1..AGENTS_COUNT) {
        val next = ((i % AGENTS_COUNT) + 1)
        val prev = ((i - 2 + AGENTS_COUNT) % AGENTS_COUNT + 1)
        val linkedAgents = mutableSetOf(next, prev)
        linkedAgentsById[i.toString()] = linkedAgents.map { it.toString() }.toMutableSet()
    }
    linkedAgentsById["1"]!!.add("3")
    linkedAgentsById["3"]!!.add("1")

    val runtime = Runtime.instance()
    val profile = ProfileImpl()
    profile.setParameter(Profile.MAIN_HOST, "localhost")
    profile.setParameter(Profile.MAIN_PORT, "10098")
//    profile.setParameter(Profile.GUI, "true")
    val mainContainer = runtime.createMainContainer(profile)
    val agents = (1..AGENTS_COUNT).map { i ->
        mainContainer.createNewAgent(i.toString(), "agents.DefaultAgent", null)
    }
    agents.forEach { agent ->
        agent.start()
    }
}
