package guiom.stream;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("stream")
public class StreamApi {
    
    @GET
    @Path("/version")
    @Produces(MediaType.TEXT_PLAIN)
    public String version() {
    	return "0.1\n";
    }
    
    @GET
    @Path("/time")
    @Produces(MediaType.TEXT_PLAIN)
    public Response streamMe() {
    	StreamingOutput stream = os -> {
    		try {
	    		Writer writer = new BufferedWriter(new OutputStreamWriter(os));
	    		writer.write("Hello\n");
	    		writer.flush();
	    		int x = 0;
	    		while(x < 50) {
	    			x ++;
	    			writer.write("It's " + LocalDateTime.now() + "\n");
	    			writer.flush();
	    			TimeUnit.MILLISECONDS.sleep(250);
	    		}
	    		writer.write("Bye\n");
	    		writer.flush();
    		} catch(InterruptedException ex) {
    			throw new RuntimeException(ex);
    		}
    	};
    	return Response.ok(stream).build();
    }
}
