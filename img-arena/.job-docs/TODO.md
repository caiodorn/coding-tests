<div style="text-align: justify">Please develop a stand alone Java application that receives JSON data regarding golf tournaments in a RESTful fashion, either to one or to multiple endpoints. The data will arrive from two different sources, in different formats (see examples below), and must therefore be processed differently depending on the source. The data will then be persisted to a database of your choice in a consistent format/to the same data structure (e.g. golf_tournament table(s)). The application should meet the following requirements at a minimum:</div>

<br>

 - You should think of an efficient and scalable way in which to consistently recognise which source the data has arrived from (this might be something that the API demands of the source) and load appropriate classes, plugins, templates or configuration that maps the incoming data to the desired internal format.
 - Your solution should allow for additional sources to be accommodated in the future with minimal change to existing code and this should be documented. 
 -  From each data source, you must persist at least this base set of data: an external ID for the tournament; start and finish dates for the tournament; the golf course name; the country hosting the tournament; the source of the tournament data; the number of rounds to be played.
 - The resultant data should be stored in a way that will allow it to be queried flexibly in the future for use in front end applications.

<div style="text-align: justify">Nothing in the test is designed to be overly complex nor to catch you out. We are interested in your ability to solve a problem in a clear, concise and reliable fashion but want to offer some flexibility and decision making around how you approach it. Your submission should be made available via github. The following is a list of aspects that we particularly value:</div>
<br>

 - Test coverage and tooling; integration and unit
 - Readable and consistent code
 - Flexibility of the solution
 - Clear instructions for running and using your application
 - Dependency selection and management

Data Source 1

    {
    	"tournamentId": "174638",
    	"tournamentName": "Women's Open Championship",
    	"forecast": "fair",
    	"courseName": "Sunnydale Golf Course",
    	"countryCode": "GB",
    	"startDate": "09/07/21",
    	"endDate": "13/07/21",
    	"roundCount": "4"
    }

Data Source 2

    {
        "tournamentUUID":"southWestInvitational",
        "golfCourse":"Happy Days Golf Club",
        "competitionName":"South West Invitational",
        "hostCountry":"United States Of America",
        "epochStart":"1638349200",
        "epochFinish":"1638468000",
        "rounds":"2",
        "playerCount":"35"
    }