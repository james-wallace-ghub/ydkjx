package org.kjtw.structures;

import java.util.ArrayList;
import java.util.List;

import com.kreative.ksfl.KSFLUtilities;

public class GameTemplate {
List<GTEntry> template;


public GameTemplate(byte[] data) {
	template = new ArrayList<GTEntry>();
	int questnum = KSFLUtilities.getInt(data, 0);
	
	for (int i=0; i <questnum; i++)
	{	
		int seekval =4 + (i*20);
		template.add(new GTEntry(data,seekval));	
	}	
	}

public String toString(){
	StringBuilder sb = new StringBuilder();

	int i=0;
	for (GTEntry gte : template)
	{
		sb.append("Question "+(i+1)+":"+System.lineSeparator());
		sb.append("Set to use :");
		if (gte.set == 0x2a)
		{
			sb.append("ALL"+System.lineSeparator());
		}
		else
		{
			sb.append(gte.set+System.lineSeparator());
		}
		sb.append("Intro to use :");
		if (gte.intro == 0x2a)
		{
			sb.append("ALL"+System.lineSeparator());
		}
		else
		{
			sb.append(gte.intro+System.lineSeparator());
		}
		sb.append("Question Type :");
		if (gte.qtype == 0x2a)
		{
			sb.append("ANY"+System.lineSeparator());
		}
		else
		{
			switch (gte.qtype)
			{
				case 0:
				{
					sb.append("Standard(Shorty)"+System.lineSeparator());
					break;
				}
				case 2:
				{
					sb.append("Gibberish"+System.lineSeparator());    											
					break;
				}
				case 3:
				{
					sb.append("Dis or Dat"+System.lineSeparator());    											
					break;
				}
				case 4:
				{
					sb.append("Jack Attack"+System.lineSeparator());    											
					break;
				}
				case 5:
				{
					sb.append("Fiber Optic Setup"+System.lineSeparator());    											
					break;
				}
				case 6:
				{
					sb.append("Fiber Optic"+System.lineSeparator());    											
					break;
				}
				case 7:
				{
					sb.append("Round 1 Wrapup"+System.lineSeparator());    											
					break;
				}
				case 10:
				{
					sb.append("Celebrity Collect Call Setup"+System.lineSeparator());    											
					break;
				}
				case 11:
				{
					sb.append("Celebrity Collect Call"+System.lineSeparator());    											
					break;
				}
				case 12:
				{
					sb.append("ThreeWay"+System.lineSeparator());    											
					break;
				}
		}   								
	}
		sb.append("Difficulty :");
		if (gte.diff == 0x2a)
		{
			sb.append("ANY"+System.lineSeparator());
		}
		else
		{
			sb.append(gte.diff+System.lineSeparator());
		}
		sb.append("Topic :");
		if (gte.topic == 0x2a)
		{
			sb.append("ANY"+System.lineSeparator());
		}
		else
		{
			sb.append("Code "+gte.topic+" (Contact jackofallplatforms@gmail.com)"+System.lineSeparator());
		}
		sb.append("Format :");
		if (gte.format == 0x2a)
		{
			sb.append("ANY"+System.lineSeparator());
		}
		else
		{
			switch (gte.format)
			{
				case 1:
				{
					sb.append("Plain (4 option)"+System.lineSeparator());
					break;
				}
				case 2:
				{
					sb.append("Fill in the blank/Interruptible Celebrity Collect Call"+System.lineSeparator());    											
					break;
				}
				case 3:
				{
					sb.append("Whatshisname"+System.lineSeparator());    											
					break;
				}
				case 4:
				{
					sb.append("Picture Question"+System.lineSeparator());    											
					break;
				}
				case 5:
				{
					sb.append("Audio Question"+System.lineSeparator());    											
					break;
				}
				case 6:
				{
					sb.append("Guest Host Question"+System.lineSeparator());    											
					break;
				}
			}    								
		}
	}
	return sb.toString();
}
	
}
