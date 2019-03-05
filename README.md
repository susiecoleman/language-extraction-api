# Language Extraction API

An API for extracting Persian text

This API is written in [scala](https://www.scala-lang.org/) 2.12 and uses the [play framework](https://www.playframework.com/)(version 2.6). [ScalaTest](http://www.scalatest.org/) is used for testing. This project also uses the [Circe](https://circe.github.io/circe/) library for JSON parsing

## Running the Code

1. Make sure you have [SBT](https://www.scala-sbt.org/) installed. SBT [installation instructions](https://www.scala-sbt.org/0.13/docs/Setup.html)
2. To start the server locally run `sbt run`. The app will be available at `http://localhost:9000/`.
3. To run the tests run `sbt test`

## Using the API

The API provides an endpoint to extract Persian text from input data.

### Endpoints

- POST `/extractLanguage`

Takes some text and filters it into Persian (`matches`) and Not Persian (`notAMatch`)

Input: 
```json
{
  "text": "Line1\nLine2\nLine3"
}

```
Response:
```json
{
  "matches": "Persian Text",
  "notAMatch": "The rest of the text"
}

```

#### cURL

cURL example. Assuming that you are running the API locally and have a `data.json` file locally.

```
curl -X POST \
  http://localhost:9000/extractLanguage \
  -H 'Content-Type: application/json' \
  -d '@data.json'
```

Example providing the JSON data as a string.

```
curl -X POST \
  http://localhost:9000/extractLanguage \
  -H 'Content-Type: application/json' \
  -d '{"text": "someText"}'
```

### Assumptions

- The `text` received by the API will contain a maximum of 2 languages. And if there are 2 languages one will be Persian. 

- The Persian language filter is based on the Unicode Range for Arabic Script. The API will not be able to filter on different languages if they both use Arabic characters 

- That text in the json passed to the endpoint will denote new lines in a passage of text (e.g. a script) using either `\n` or `\r`

- That a single line would not be expected to contain a mix of Persian and Non Persian and that the presence of Persian characters is an indicator of the line being in Persian

- That a line that contains only numbers cannot be assumed to be Persian 

- That latin punctuation used in Persian, e.g. full stops, should not be used as an indicator of Persian

- The API makes no attempt to extract non spoken text from a script or to match the Persian text to the corresponding Non Persian Text.

### Future Extensions

- Add more language filters to the `LanguageFilters.scala` file.

- Update the `/extractLanguage` endpoint to allow users to specify which language they are detecting. e.g. `/extractLanguage/persian`