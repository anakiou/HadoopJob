package ch.cern.hadoopJob;

import ch.cern.hadoopJob.mapper.MaxTemperatureMapper;
import ch.cern.hadoopJob.reducer.MaxTemperatureReducer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Simple map reduce job
 *
 */
public class MaxTemperatureJob 
{
    public static void main( String[] args ) throws Exception
    {
        if(args.length == 0){
        	System.err.println("Usage: MaxTemperature");
		System.exit(-1);
        }

	Job job = new Job();
	job.setJarByClass(MaxTemperatureJob.class); 
	job.setJobName("Max Temperature Job");

	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));

	job.setMapperClass(MaxTemperatureMapper.class);
	job.setReducerClass(MaxTemperatureReducer.class);

	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(LongWritable.class);

	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
