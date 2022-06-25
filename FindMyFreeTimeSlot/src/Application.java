import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
public class Application {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		//calendar of A= [['9:00','10:30'], ['12:00', '13:00'], ['16:00', '18:00']]
		//available = ['9:00', '20:00']
		//calendar of B= [['10:00', '11:30'], ['12:30', '14:30'], ['14:30', '15:00'], ['16:00', '17:00']]
		//available= ['10:00', '18:30']
		//sample output= [['11:30', '12:00'], ['15:00', '16:00'], ['18:00', '18:30']]
		
		ArrayList<List<String>> schedule_of_A = new ArrayList<List<String>>();
		schedule_of_A.add(List.of("9:00","10:30"));
		schedule_of_A.add(List.of("12:00","13:00"));
		schedule_of_A.add(List.of("16:00","18:00"));
		List<String> availabe_range_of_A= List.of("9:00","20:00");
		
		ArrayList<List<String>> schedule_of_B = new ArrayList<List<String>>();
		schedule_of_B.add(List.of("10:00","11:30"));
		schedule_of_B.add(List.of("12:30","14:30"));
		schedule_of_B.add(List.of("14:30","15:00"));
		schedule_of_B.add(List.of("16:00","17:00"));
		List<String> availabe_range_of_B= List.of("10:00","18:30");
		
		int required_meeting_duration= 30;
		
		ArrayList<List<String>> meeting_slots_between_A_and_B = new ArrayList<List<String>>();
		
//		System.out.println("schedule of A is "+schedule_of_A);
//		System.out.println("schedule of B is "+schedule_of_B);
		
		//only fetch the available time
		keep_only_the_available_times(schedule_of_A, availabe_range_of_A);
		keep_only_the_available_times(schedule_of_B, availabe_range_of_B);
		
//		System.out.println("Pre-processed schedule of A is "+schedule_of_A);
//		System.out.println("Pre-processed schedule of B is "+schedule_of_B);
		
		
		get_meeting_slots( schedule_of_A, availabe_range_of_A,schedule_of_B, availabe_range_of_B,required_meeting_duration, meeting_slots_between_A_and_B);
		System.out.println(meeting_slots_between_A_and_B);
	}

	private static void get_meeting_slots(ArrayList<List<String>> schedule_of_A, List<String> availabe_range_of_A,
			ArrayList<List<String>> schedule_of_B, List<String> availabe_range_of_B, int required_meeting_duration, ArrayList<List<String>> meeting_slots_between_A_and_B) throws ParseException {
		// TODO Auto-generated method stub
		List<String> first_availabe_range_next_in_A= schedule_of_A.get(0);
		
		long first_slot_diff = get_difference( availabe_range_of_A.get(0),first_availabe_range_next_in_A.get(0));
//		
		int first_number_of_meeting_slots= number_of_meeting_slots_availble(first_slot_diff, convert_time_to_string(required_meeting_duration));
		
//		for(int i=1; i<=first_number_of_meeting_slots;i++)
		if(first_number_of_meeting_slots>0)
			check_if_available_in_B(schedule_of_B, availabe_range_of_B, availabe_range_of_A.get(0), required_meeting_duration, first_availabe_range_next_in_A.get(0), meeting_slots_between_A_and_B);
		
		for(List<String> meeting_range_in_A: schedule_of_A) {

			if((schedule_of_A.indexOf(meeting_range_in_A)+1)<schedule_of_A.size()) {
//				System.out.println("pop: "+ schedule_of_A.get(schedule_of_A.indexOf(meeting_range_in_A)+1)+" index: "+ schedule_of_A.indexOf(meeting_range_in_A));
			
				List<String> availabe_range_next_in_A= schedule_of_A.get(schedule_of_A.indexOf(meeting_range_in_A)+1);
				long slot_diff = get_difference(meeting_range_in_A.get(1), availabe_range_next_in_A.get(0));
				
				int number_of_meeting_slots= number_of_meeting_slots_availble(slot_diff, convert_time_to_string(required_meeting_duration));
				
//				for(int i=1; i<=number_of_meeting_slots;i++)
				if(number_of_meeting_slots>0)
					check_if_available_in_B(schedule_of_B, availabe_range_of_B, meeting_range_in_A.get(1), required_meeting_duration, availabe_range_next_in_A.get(0), meeting_slots_between_A_and_B);
			}
		}
		//check for the end
		long last_slot_diff= get_difference(schedule_of_A.get(schedule_of_A.size()-1).get(1), availabe_range_of_A.get(1));
		if(number_of_meeting_slots_availble(last_slot_diff, convert_time_to_string(required_meeting_duration))>0) {
			check_if_available_in_B(schedule_of_B, availabe_range_of_B, schedule_of_A.get(schedule_of_A.size()-1).get(1), required_meeting_duration, availabe_range_of_A.get(1), meeting_slots_between_A_and_B);
		}
		
		return ;
	}

	private static void check_if_available_in_B(ArrayList<List<String>> schedule_of_B, List<String> availabe_range_of_B, String start_meeting_time_available_for_A,
			int required_meeting_duration, String end_meeting_time_available_for_A, ArrayList<List<String>> meeting_slots_between_A_and_B ) throws ParseException {
		// TODO Auto-generated method stub
//		System.out.println(schedule_of_B+" "+availabe_range_of_B+" "+start_meeting_time_available_for_A+" "+ required_meeting_duration+ " "+ end_meeting_time_available_for_A );
		
		//meeting_time_available_for_A is within  availabe_range_of_B
		if(get_difference(end_meeting_time_available_for_A, availabe_range_of_B.get(0))> 0) 
			return ;
		else {
			//this means check the difference between renage_of_b[0] and 1st_element_of_B
			long base_slot_diff = get_difference( start_meeting_time_available_for_A,availabe_range_of_B.get(0));
			String base_time_of_slot= (base_slot_diff>0)?availabe_range_of_B.get(0):start_meeting_time_available_for_A;

			long slot_diff = get_difference( base_time_of_slot,schedule_of_B.get(0).get(0));
			int number_of_meeting_slots=number_of_meeting_slots_availble(slot_diff, convert_time_to_string(required_meeting_duration));
			if(number_of_meeting_slots>0) {
				//add to the meeting slot
//				System.out.println(base_time_of_slot+" "+ schedule_of_B.get(0).get(0));
				meeting_slots_between_A_and_B.add(List.of(base_time_of_slot,schedule_of_B.get(0).get(0)));
			}
		}
		for(List<String> meeting_range_in_B: schedule_of_B) {
			//start_A > end_B OR start_B > end_A, then reject
			if((schedule_of_B.indexOf(meeting_range_in_B)+1)<schedule_of_B.size()) {
				long slot_diff_one = get_difference(start_meeting_time_available_for_A, schedule_of_B.get(schedule_of_B.indexOf(meeting_range_in_B)+1).get(0));
				long slot_diff_two = get_difference(meeting_range_in_B.get(1), end_meeting_time_available_for_A);
				if(!(slot_diff_one<=0 || slot_diff_two<=0))  
				{
					long slot_diff = get_difference( meeting_range_in_B.get(1),schedule_of_B.get(schedule_of_B.indexOf(meeting_range_in_B)+1).get(0));
					int number_of_meeting_slots=number_of_meeting_slots_availble(slot_diff, convert_time_to_string(required_meeting_duration));
					if(number_of_meeting_slots>0) {
						
						//take max of inital slot
						long slot_diff_min= get_difference(start_meeting_time_available_for_A, meeting_range_in_B.get(1));
						//take min of the end slot
						long slot_diff_max= get_difference(end_meeting_time_available_for_A, schedule_of_B.get(schedule_of_B.indexOf(meeting_range_in_B)+1).get(0));
						
						//and find if >30
						String inital_time_of_slot= (slot_diff_min>0)?meeting_range_in_B.get(1):start_meeting_time_available_for_A;
						String final_time_of_slot= (slot_diff_max>0)?end_meeting_time_available_for_A:schedule_of_B.get(schedule_of_B.indexOf(meeting_range_in_B)+1).get(0);
						
						//and find slot diff>1, make two slots
						long slot_diff_final = get_difference(inital_time_of_slot,  final_time_of_slot);
//						System.out.println(inital_time_of_slot+ " "+ final_time_of_slot);
						int number_of_meeting_slots_final=number_of_meeting_slots_availble(slot_diff_final, convert_time_to_string(required_meeting_duration));
						if(number_of_meeting_slots_final>0) {
//							System.out.println("no of slots avaialable: "+number_of_meeting_slots_final);
							meeting_slots_between_A_and_B.add(List.of(inital_time_of_slot,final_time_of_slot));
						}
						
					}
				}
			}
		}
		long last_diff= get_difference(schedule_of_B.get(schedule_of_B.size()-1).get(1), availabe_range_of_B.get(1));
		
		if(number_of_meeting_slots_availble(last_diff, convert_time_to_string(required_meeting_duration))<=0)
			return;
		else {
			long f_diff_between_A_and_B= get_difference(start_meeting_time_available_for_A, schedule_of_B.get(schedule_of_B.size()-1).get(1));
			String f_last_meeting_point= (f_diff_between_A_and_B>0)?schedule_of_B.get(schedule_of_B.size()-1).get(1):start_meeting_time_available_for_A;
			
			long l_diff_between_A_and_B= get_difference(end_meeting_time_available_for_A, availabe_range_of_B.get(1));
			String last_meeting_point= (l_diff_between_A_and_B>0)?end_meeting_time_available_for_A:availabe_range_of_B.get(1);
			
			long diff_between_last_and_B= get_difference(f_last_meeting_point, last_meeting_point);
			
			if(number_of_meeting_slots_availble(diff_between_last_and_B, convert_time_to_string(required_meeting_duration))>0) {
//				System.out.println(f_last_meeting_point +" "+last_meeting_point);
				meeting_slots_between_A_and_B.add(List.of(f_last_meeting_point,last_meeting_point));
			}

		}
		
	}

	private static String convert_time_to_string(int required_meeting_duration) {
		// TODO Auto-generated method stub
		String startTime = "00:00";
		int minutes = required_meeting_duration;
		int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
		int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
		String newtime = h+":"+m;
		return newtime;
	}

	private static int number_of_meeting_slots_availble(long slot_diff, String required_meeting_duration) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date b2 = sdf.parse(required_meeting_duration);
	    
	    long required_meeting_timestamp= b2.getTime()<0?b2.getTime()*-1:b2.getTime();
	    
		int no_of_available_slots = (int) (slot_diff/ (required_meeting_timestamp/10));
		
//		System.out.println(no_of_available_slots);
		return no_of_available_slots;
		
//		return 0;
	}

	private static void keep_only_the_available_times(ArrayList<List<String>> schedule,
			List<String> availabe_range) throws ParseException {
		
		Iterator it = schedule.iterator();
		while (it.hasNext()) {
			
			List<String> meeting_range= (List<String>) it.next();
		    long base_diff = get_difference(meeting_range.get(0), availabe_range.get(0));
		    long limit_diff = get_difference(meeting_range.get(1), availabe_range.get(1));
		    
			if(base_diff>0 || limit_diff<0) {
				//pop
//				System.out.println("pop: "+ meeting_range + " index: "+ schedule.indexOf(meeting_range));
				it.remove();
			}
		}
		 
        
	}

	private static long get_difference(String string1, String string2) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date b1 = sdf.parse(string1);
	    Date b2 = sdf.parse(string2);
	    long base_diff = b2.getTime() - b1.getTime(); 
	    
		return base_diff;
	}

}
