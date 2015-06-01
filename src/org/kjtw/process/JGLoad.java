package org.kjtw.process;

import java.io.File;
import java.io.IOException;

import org.kjtw.structures.JackGraphic;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.SoundResource;

public class JGLoad{
	JackGraphic jg;
	SoundResource sr;
	BerkeleyResourceFile rp;
	
	public JGLoad(String loc) throws IOException
	{
		File path = new File(loc);
		rp = null;
		try {
			rp = new BerkeleyResourceFile(path, "r", MacResourceFile.CREATE_NEVER);
		} catch (IOException e) {
			System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
			return;
		}
	}
	
	public void setRes(short file, short sound, String palette)
	{
		MacResource r = rp.get(KSFLUtilities.fcc("off4"), file);

		jg = new JackGraphic(r.data,palette);
				
		r = rp.get(KSFLUtilities.fcc("snd"), sound);
		sr = r.shallowRecast(SoundResource.class);
	}

	public SoundResource getSnd() {
		return sr;
	}

	public JackGraphic getGfx() {
		return jg;
	}
}