# Compatibility Predictor

## Instructions
My solution takes input via a JSON HTTP POST request body sent to the index ("/") endpoint. The JSON structure is made up exactly like in the example where it takes a list of team members and applicants with their name and attributes as properties.

Then, the controller retrieves the JSON string, and with the help of Spring web, it is converted to a POJO. From there, all the data is able to be used through my service class which runs the calculations of compatibility.

My calculation scores the applicants based on 2 factors:

1. How well they complement the team (determined by standard deviation)
2. How much value they add to the team (determined by the difference in the sum of the average teams attribute scores and the sum of the teams average attribute scores plus applicant scores)

Once all the applicants compatibilites are calculated, it is returned to the client via json.

## Example
