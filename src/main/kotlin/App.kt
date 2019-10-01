package agents

import jade.core.Profile
import jade.core.ProfileImpl
import jade.core.Runtime


const val AGENTS_COUNT = 7


fun main() {
    val runtime = Runtime.instance()
    val profile = ProfileImpl()
    profile.setParameter(Profile.MAIN_HOST, "localhost");
    profile.setParameter(Profile.MAIN_PORT, "10098")
    profile.setParameter(Profile.GUI, "true")
    val mainContainer = runtime.createMainContainer(profile)
    val agents = (1..AGENTS_COUNT).map { i ->
        mainContainer.createNewAgent(
            i.toString(),
            "agents.DefaultAgent",
            null
        )
    }
    agents.forEach { agent ->
        agent.start()
    }
}
