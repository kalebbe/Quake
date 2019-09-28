/**
 * This is the driving class that consumes an API from the
 * earthquake website to retrieve earthquake data. In future
 * milestones, this IoT app will create objects out of each
 * consumed earthquake to return them to the reporting website.
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using Newtonsoft.Json.Linq;

namespace Quake
{
    class Program
    {
        //To be used for parsing earthquakes into objects later on
        private List<Earthquake> earthquakes;

        //Query to get json data from earthquake website. Defaults to past 30 days.
        private const string url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";

        /**
         * This is the main method for this IoT application.
         * 
         * @param string[]
         * @return void
         */
        static void Main(string[] args)
        {
            //This sends a request to the API with the desired parameters.
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            try
            {
                //This is the response from the server
                WebResponse response = request.GetResponse();

                //We're currently using stream and streamreader to retrieve the data
                //then parse it into json. This is a jerry rigged way to do this and
                //will also be fixed in a future milestone. The response should be
                //written in geojson already, so this parsing is unecessary.
                using (Stream responseStream = response.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(responseStream, Encoding.UTF8);
                    JObject parsed = JObject.Parse(reader.ReadToEnd());

                    foreach(var p in parsed)
                    {
                        Console.WriteLine("{0}: {1}", p.Key, p.Value);
                    }
                }
            }
            //Catching a negative response from the server.
            catch (WebException ex)
            {
                WebResponse errorResponse = ex.Response;
                using (Stream responseStream = errorResponse.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(responseStream,Encoding.GetEncoding("utf-8"));
                    String errorText = reader.ReadToEnd();
                }
                throw;
            }
        }
    }
}
