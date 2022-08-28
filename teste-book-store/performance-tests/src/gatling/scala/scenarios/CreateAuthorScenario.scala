package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import simulations.BaseSimulation

object CreateAuthorScenario extends BaseSimulation {

  val create: ScenarioBuilder = scenario("Create Author")
    .exec(http("Create Author")
      .post("/authors")
      .basicAuth("bootcamp", "vempratqi")
      .header("Content-type", "application/json")
      .body(StringBody("""{"name":"author 1234234", "description":"description 1243324"}"""))
      .check(status.is(201))
      .check(jsonPath("$.code").saveAs("authorCode"))
    )

    .exec { session =>
      println("Código do Author: --> " + session("authorCode").as[String].trim)
      session
    }
}
