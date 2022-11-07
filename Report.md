Coles Code Challenge for Software Engineer

Solution:

Java SE 17 (LTS)
Spring Boot
Spring Webflux
Webclient consumer
Maven
IntelliJ IDEA

I used Spring Initializer to create a Spring Webflux project for a Reactive Restful Web Service to solve this problem and expose 
one Rest End point "/music" with query parameter "artistname".

MusicController handles the get mapping and calls the MusicService to return either collection of artists or 
collection of releases.

First I query the musicbrainz API ("/artist" end point) with the provided artist/ band name  and obtain artist response wrapped in a mono.
Then, used method chaining using map method to read the count of artist in the response.
Then, if the count is more than one, I extract "artists" field from the artist response and return it.
Else if the count of artist is one, first I extract ["artist"]["id"] field and then query the musicbrainz API
("/releases") end point with artist id and then extract the "releases" field from Release response and return it.

Assumptions:

The provided artist/ band name is a valid string and has an entry in the musicbrainz database

Design Decision:

I used the latest Spring Webflux as RestTemplate is deprecated.

I converted Webclient response body to JsonNode rather than custom classes for artist and release responses.
This meant I need to manually map the fields obtained from the response, but I could use the same function to
return either the collection of artists or collection of artists.

I did postprocessing on the webclient response synchronously without blocking but effectively using map to achieve it.

Server port config is set in application.properties file inside "resources" folder.

Further Work with More Time:

1. Preprocessing the query parameter of the endpoint "artistname" to ensure it contains only alphabets and trim any leading or trailing whitespaces
2. Error handling in the MusicService class to handle error status on response from musicbrainz api
3. Use database to store the response fetched from musicbrainz and then any future requests will be looked up in the database before querying musicbrainz api
4. Build front end dynamic content with response from the "/music" endpoint.
5. Add tests to test the "/music" Rest endpoint