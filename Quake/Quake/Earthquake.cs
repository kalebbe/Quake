/**
 * This is the object class for the earthquakes being 
 * consumed by the API.
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

using System;

namespace Quake
{
    class Earthquake
    {
        private double magnitude;
        private DateTime dateTime;
        private double depth;
        private String location;
        private String coordinates;

        //Constructor for the earthquake class to instantiate earthquakes.
        public Earthquake(double magnitude, DateTime dateTime, double depth, 
            String location, String coordinates)
        {
            this.magnitude = magnitude;
            this.dateTime = dateTime;
            this.depth = depth;
            this.location = location;
            this.coordinates = coordinates;
        }

        //Getters and setters for all of the variables in the Earthquake class
        public double Magnitude { get => magnitude; set => magnitude = value; }
        public DateTime DateTime { get => dateTime; set => dateTime = value; }
        public double Depth { get => depth; set => depth = value; }
        public String Location { get => location; set => location = value; }
        public String Coordinates { get => coordinates; set => coordinates = value; }

        //Custom ToStrint made for testing purposes and to show the user successful XML Parsing
        public override string ToString()
        {
            return String.Format("Magnitude: {0}\nDateTime: {1}\nDepth: {2}\nLocation: {3}\nCoordinates: {4}\n\n\n", 
                magnitude, dateTime, depth, location, coordinates) ;
        }
    }
}
