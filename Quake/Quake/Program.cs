/**
 * This is the driving class that consumes an API from the
 * earthquake website to retrieve earthquake data. Originally,
 * this would have been using GeoJSON from the API, but we came
 * across the time in the GeoJSON and it is broken. Because of
 * this, we had to proceed using XML and XML parsing for this
 * portion of the project.
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Threading;
using System.Xml;
using Newtonsoft.Json;

namespace Quake
{
    class Program
    {
        //https://earthquake.usgs.gov/fdsnws/event/1/ is the API documentation for this API
        //Query to get xml data from earthquake website.
        private const string url = "https://earthquake.usgs.gov/fdsnws/event/1/query?starttime=";

        /**
         * This is the main method for this IoT application.
         * 
         * @param string[]
         * @return void
         */
        static void Main(string[] args)
        {
            while (true) //Creates and infinite loop
            {
                //Converts current datetime to -1 hour and UTC in a String
                String fromDate = DateTime.Now.AddHours(-1).ToUniversalTime().ToString();

                //This sends a request to the API with the desired parameters.
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url + fromDate);
                try
                {
                    //This is the response from the server
                    WebResponse response = request.GetResponse();

                    //Reading the response
                    using (var sr = new StreamReader(response.GetResponseStream()))
                    {
                        //XML was used because the TIME property in the GeoJSON API was broken
                        XmlDocument doc = new XmlDocument();
                        try
                        {
                            //Response gets split into a list of earthquakes
                            List<Earthquake> quakes = new List<Earthquake>();

                            doc.Load(sr);

                            //Every individual earthquake event. Used to retrieve count
                            XmlNodeList events = doc.GetElementsByTagName("event");

                            //Lists of different earthquake properties
                            XmlNodeList magnitude = doc.GetElementsByTagName("mag");
                            XmlNodeList datetime = doc.GetElementsByTagName("time");
                            XmlNodeList depth = doc.GetElementsByTagName("depth");
                            XmlNodeList location = doc.GetElementsByTagName("description");
                            XmlNodeList latitude = doc.GetElementsByTagName("latitude");
                            XmlNodeList longitude = doc.GetElementsByTagName("longitude");                           
                            
                            //Getting each index in order
                            for(int i = 0; i < events.Count; i++)
                            {
                                String[] dateSplit = datetime[i].InnerText.Split('T'); //Everything before the T is the date
                                String[] timeSplit = dateSplit[1].Split('.'); //Everything after the period is milliseconds, so it is split off

                                //Parsing the date into a C# readable datetime
                                DateTime dt = DateTime.ParseExact(dateSplit[0] + " " + timeSplit[0], "yyyy-MM-dd HH:mm:ss", 
                                    System.Globalization.CultureInfo.InvariantCulture);

                                //Creating a new earthquake object for each parsed event
                                quakes.Add(new Earthquake(double.Parse(magnitude[i]["value"].InnerText), dt, 
                                    double.Parse(depth[i]["value"].InnerText), location[i]["text"].InnerText, 
                                    latitude[i].InnerText + ", " + longitude[i].InnerText));
                            }

                            quakes.ForEach(Console.WriteLine); //Prints each quake object

                            //This converts the list of objects into a json string for transferrence
                            String json = JsonConvert.SerializeObject(quakes).ToLower();

                            try //Starting another request. Used another try catch for system exceptions
                            {
                                //Request to post to our website using the key "QuakeKey530". In a real application, this would use a token
                                //generator.
                                request = (HttpWebRequest)WebRequest.Create("http://localhost:8080/Quake.info/rest/quakes/postdata/QuakeKey530");
                                request.ContentType = "application/json";
                                request.Method = "POST";

                                //Rights the json string to the http request
                                using (var sw = new StreamWriter(request.GetRequestStream()))
                                {
                                    sw.Write(json);
                                }

                                //Retrieving the response from the POST (Http response codes)
                                var httpResponse = (HttpWebResponse)request.GetResponse();
                                using (var str = new StreamReader(response.GetResponseStream()))
                                {
                                    var result = str.ReadToEnd();
                                    Console.WriteLine(result.ToString());
                                }
                            } catch(Exception e)
                            {
                                Console.WriteLine(e.ToString());
                            }
                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e.ToString());
                        }
                    }
                }
                //Catching a negative response from the server.
                catch (WebException ex)
                {
                    WebResponse errorResponse = ex.Response;
                    using (Stream responseStream = errorResponse.GetResponseStream())
                    {
                        StreamReader reader = new StreamReader(responseStream, Encoding.GetEncoding("utf-8"));
                        String errorText = reader.ReadToEnd();
                    }
                    throw;
                }

                //Application sleeps for an hour between each data retrieval. This could
                //be decreased or increased per project requirements and direction.
                Thread.Sleep(3600*1000);
            }
        }
    }
}
