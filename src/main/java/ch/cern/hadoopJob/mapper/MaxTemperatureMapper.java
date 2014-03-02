package ch.cern.hadoopJob.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
		
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
                
		if(!line.contains(":")){
		  return;
		}
                  
		String[] tokens = line.split(":");
		tokens = tokens[1].split(" ");
		
		if(tokens.length > 2){
		context.write(new Text(tokens[0]), new LongWritable(Long.parseLong(tokens[2])));
		}
	}
}
