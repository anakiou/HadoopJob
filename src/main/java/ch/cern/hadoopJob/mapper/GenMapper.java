package ch.cern.hadoopJob.mapper;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.commons.lang.StringEscapeUtils;

public class GenMapper extends Mapper<Object, Text, Text, IntWritable> {
		
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
	
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		Map<String, String> parsed = transformXmlToMap(value.toString());

		String txt = parsed.get("Text");

		if(txt == null){
                  return;
		}

                txt = StringEscapeUtils.unescapeHtml(txt.toLowerCase());
                txt = txt.replaceAll("'","");
                txt = txt.replaceAll("[^a-zA-Z]", " ");

		StringTokenizer itr = new StringTokenizer(txt);
		while(itr.hasMoreTokens()){
		   word.set(itr.nextToken());
		   context.write(word, one);
		}
	}

    private Map<String, String> transformXmlToMap(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] tokens = xml.trim().substring(5, xml.trim().length() - 3)
					.split("\"");

			for (int i = 0; i < tokens.length - 1; i += 2) {
				String key = tokens[i].trim();
				String val = tokens[i + 1];

				map.put(key.substring(0, key.length() - 1), val);
			}
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println(xml);
		}

		return map;
	}
}
