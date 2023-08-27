# GitHub API Data Retrieval

This project is a Java17/Spring Boot 3 application that retrieves repository and branch information from the [GitHub API](https://docs.github.com/en/rest?apiVersion=2022-11-28) for a specified user.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Endpoint](#api-endpoint)
- [Author](#author)
- [License](#license)

## Features

- Fetches repository information for a specified GitHub user.
- Retrieves branch information for each repository, ignoring forks and handling paginated responses.
- Returns a JSON response containing repository names, user login, and branch details with latest commit SHA.

## Getting Started

Follow these steps to set up and run the project locally:

1. Install dependencies using Maven:
```
mvn clean install
```
2. Run the Spring Boot application:
```
mvn spring-boot:run
```


## Usage

Once the application is running, you can access the API endpoint at port 8080 to retrieve data.

## API Endpoint

To fetch repository and branch information for a GitHub user, make a GET request to:
```
http://localhost:8080/{user}
```
Where {user} is GitHub username

## Author
- Remigiusz Katryński [GitHub](https://github.com/Krucii)

## License

This project is licensed under the MIT License.



Copyright 2023 Remigiusz Katryński

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE
