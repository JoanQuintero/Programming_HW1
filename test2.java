/*
	Group 4
	Members: Shany M. Lajara Contreras, Miguel Gomez, Joan S. Quintero
*/
import java.util.Random;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.io.*; 

public class test2
{

	public static class Movements_in
	{
		/*
			Static int variables for each disk scheduling algorithm 
			so that we may store the value of the total of cylinders for each 
		*/
		static int cylinders_FCFS = 0;
	    static int cylinders_SSTF = 0;
	    static int cylinders_SCAN = 0;
	    static int cylinders_C_SCAN = 0;
	    static int cylinders_LOOK = 0;
	    static int cylinders_C_LOOK = 0;
	}

	public static class Lists
	{
		// int variable to designate as the head
		static int head = 0;

		// int variable to set as the max amount of numbers in main list (size of list)
		static int queue_size = 1000;

		// size of ArrayList for our main list of random numbers
		static int limit = 1000; 

		//Main list
		//static ArrayList<Integer> main_list = new ArrayList<Integer>(limit);

		//declaring ArrayList with initial size of set limit above
		static ArrayList<Integer> main_list = new ArrayList<Integer>();

	}

	public static void main(String [] args)
	{

		// getting argument from the terminal this way 
		int argument = Integer.parseInt(args[0]);
		/* 
			#1) We are making sure that we are receiving only one argument
			#2) We are making sure the argument is positive
			#3) if 1 and 2 are true, then we continue
		*/
		if (argument > 0 && args.length == 1)
		{
			// Make head = command input
			Lists.head = argument;

			// // We are making an instance of Random()
			// Random random_numbers = new Random();

			// // Obtain a number between [0 - 4999].
			// for  (int i = 0; i < Lists.queue_size; i++)
			// {
			// 	int highest = 1000;  // As stated on the problem, the random numbers are only going up to 999
			// 	int random_number = random_numbers.nextInt(highest);
			// 	// Appending the new random element at the end of the list
			// 	Lists.main_list.add(random_number); 
			// }
		Lists.main_list.add(95);
		Lists.main_list.add(180);
		Lists.main_list.add(34);
		Lists.main_list.add(119);
		Lists.main_list.add(11);
		Lists.main_list.add(123);
		Lists.main_list.add(62);
		Lists.main_list.add(64);
		}

		// All our threads are here:
		Thread thread_FCFS = new Thread(new fcfs_thread());
		Thread thread_SSTF = new Thread(new sstf_thread());
		Thread thread_SCAN = new Thread(new scan_thread());
		Thread thread_C_SCAN = new Thread(new c_scan_thread());
		Thread thread_LOOK = new Thread(new look_thread());
		Thread thread_C_LOOK = new Thread(new c_look_thread());

		// Start all threads
		thread_FCFS.start();
		thread_SSTF.start();
		thread_SCAN.start();
		thread_C_SCAN.start();
		thread_LOOK.start();
		thread_C_LOOK.start();

		System.out.println("FCFS is good, SCAN is good, LOOK is good, C-LOOK");
	}

	// -------------------------------------------------------------------

	public static class fcfs_thread implements Runnable
	{
        //  Calculate FCFS --> First Come, First Serve

        @Override
		public void run() 
		{

			// size of ArrayList for our fcfs list 
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			list = Lists.main_list;

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value
		    distances = Math.abs(list.get(0) - Lists.head);

			for(int i=0; i<Lists.main_list.size()-1; i++)
			{
			    absolute_val = Math.abs(list.get(i) - list.get(i+1));
				distances += absolute_val;
			}

			Movements_in.cylinders_FCFS = distances; // Assign the head movements
			System.out.println("total FCFS cylinders = "+ Movements_in.cylinders_FCFS);
		} // end of void Run()
	} // end of class fcfs_thread

	// -------------------------------------------------------------------

		public static class sstf_thread implements Runnable
	{
        //  Calculate SSTF --> Shortest Seek Time First

        @Override
		public void run() 
		{

			// size of ArrayList for our sstf list 
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			
		    // Our SSTF algorithm needed a second list 
		    //to manipulate and remove items from to keep track 
		    //of head movements
		    ArrayList<Integer> separate_list = new ArrayList<Integer>();
		    
		    //list = Lists.main_list;
		    //separate_list = list;

		    // Without the following for loop, we were getting a lot of symmetry problems
			// when attempting to use Collections.sort
			// The issue was:
			/*
			java.lang.IllegalArgumentException: Comparison method violates its general contract!
			*/
			for (int element: Lists.main_list)
			{
				list.add(element); 
				separate_list.add(element);
			}

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value
		    

		    // If head is in the list, do nothing
		    if (separate_list.contains(Lists.head)) {}
		
			// if head is not in the list, add it
			else
			{
				separate_list.add(Lists.head);
			}

			// We will also sort the main sstf list
			// The reason for that is that we will be
			// comparing indexes between separate list and ssft
			Collections.sort(list);

			// Same for main list of SSTF; If head is in the list, do nothing
		    if (list.contains(Lists.head)) {}
		
			// if head is not in the list, add it
			else
			{
				list.add(Lists.head);
			}

			// sort the list in increasing order
			Collections.sort(separate_list);

			// A variable to keep track of the current value
			// It will start having the value of the head as default 
			int current = Lists.head;

			// A variable to keep track of the index of the current value
        	int index_current = 0;

        	// We will begin by iterating from 0 to the length of the SSTF List - 1
        	for (int i=0; i<list.size()-1; i++) 
        	{
        		//System.out.println(current);
        		//Making sure the code runs only if the list is not empty
        		if (list.size() > 0)
        		{
        			index_current = separate_list.indexOf(current);

        			//Checking whether the index of the default (head) value of current is 0 or not
        			if (index_current == 0)
        			{
        				absolute_val = Math.abs(current - separate_list.get(1));
        				current = separate_list.get(index_current+1);

        				// Removing the old current value 
        				// Hint: This is why we needed a separate list
        				separate_list.remove(0);
        				distances += absolute_val;
        				//System.out.println("distance"+distances);
        			}

        			else // If the index of the default index_current is not 0, 
        			//then: 
        			{
        				if // Check if: 
        				// | item before the head - head | > | item after the head - head |

        					(
        					Math.abs(current - separate_list.get(index_current-1)) 
        					>
        					Math.abs(current - separate_list.get(index_current+1)) 
        					)
        				{ // if the above statement returns true,
        					// get the absolute value of the lesser one
        					absolute_val = 
		                    Math.abs(current - separate_list.get(index_current+1));
		                    
		                    // change the current value so it can keep searching for the 
		                    //shortest seek time first
		                    current = separate_list.get(index_current+1);

		                    // Removing the old current value
		                    separate_list.remove(index_current);

		            
        				}

        				else // | item before the head - head | < | item after the head - head |
        				{ 	
        					// if the above statement next to the else opening returns true,
        					// get the absolute value of the lesser one
        					absolute_val = 
		                    Math.abs(current - separate_list.get(index_current-1));
		                    
		                    // change the current value so it can keep searching for the 
		                    //shortest seek time first
		                    current = separate_list.get(index_current-1);

		                    // Removing the old current value
		                    separate_list.remove(index_current);

        				}
        				distances += absolute_val;
        			}
        		

        		} // end of the if statement inside the for loop
        		Movements_in.cylinders_SSTF = distances; // Assign the head movements
        	} // end of the following code snipped: for (int i=0; i<Lists.sstf.size()-1; i++) 
        	//Movements_in.cylinders_SSTF = distances; // Assign the head movements
        	System.out.println("total SSTF cylinders = "+ Movements_in.cylinders_SSTF);

		} // end of void Run()
	} // end of class sstf_thread

	// -------------------------------------------------------------------

		public static class scan_thread implements Runnable
	{
        //  Calculate SCAN 
        @Override
		public void run() 
		{
			// size of ArrayList for our scan list 
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			//list = Lists.main_list;

			// Without the following for loop, we were getting a lot of symmetry problems
			// when attempting to use Collections.sort
			// The issue was:
			/*
			java.lang.IllegalArgumentException: Comparison method violates its general contract!
			*/
			for (int element: Lists.main_list)
			{
				list.add(element); 
			}

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value

		    Collections.sort(list); // Organize elements in ascending order

		    // Check if 0 is in the list, if not, add it
		    if (list.get(0) != 0)  { list.add(0); }

		    Collections.sort(list); // Re-organize elements in ascending order

		    // check if head is in the lit, if it is, do nothing
		    if (list.contains(Lists.head)) {} 
		    // but if it is not, then add it
			else {  list.add(Lists.head);  }

			Collections.sort(list); // Re-organize elements in ascending order

			// Before iterating through the list, we will reverse the list
			// In order to iterate from the index of the head, to the reversed end of the list
			Collections.reverse(list);

			//Now we iterate with the reversed conditions met
			for (int i=list.indexOf(Lists.head); i<list.size()-1; i++)
			{
			    absolute_val = Math.abs(list.get(i) - list.get(i+1));
			    distances += absolute_val;
			}

			//So far, we have accomplished to go to one end of the list
			// Since we reversed it, we accomplished going to the end at the beginning, 
			// in this case we went from head to ---> 0

			// We continue to sort again,
			Collections.sort(list);
			// The purpose is to have the list in order again, so that we can
			// do a similar iteration from the one above, but this time from ( head.index + 1 )
			// since we calculated the absolute value for the head already.

			// Very important, the distance from 0 to the next item after the head has to be
			// calculated, we do this outside of the iteration
			distances += Math.abs(0 - list.get(list.indexOf(Lists.head)+1));

			// Then we move into doing our final iteration
			for (int j=list.indexOf(Lists.head)+1; j<list.size()-1; j++)
				{
					absolute_val = Math.abs(list.get(j) - list.get(j+1));
					distances += absolute_val;
				}

			Movements_in.cylinders_SCAN = distances; // Assign the head movements
			System.out.println("total SCAN cylinders = "+ Movements_in.cylinders_SCAN);
		} // end of Run()
	} // end of scan_thread

	// -------------------------------------------------------------------

		public static class c_scan_thread implements Runnable
	{
        //  Calculate C_SCAN 

        @Override
		public void run() 
		{
			// size of ArrayList for our c-scan list 
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			//list = Lists.main_list;

			// Without the following for loop, we were getting a lot of symmetry problems
			// when attempting to use Collections.sort
			// The issue was:
			/*
			java.lang.IllegalArgumentException: Comparison method violates its general contract!
			*/
			for (int element: Lists.main_list)
			{
				list.add(element); 
			}

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value
		    
		    Collections.sort(list); // Organize elements in ascending order

		    // Check if 0 is in the list, if not, add it
		    if (list.get(0) != 0)  { list.add(0); }

		    

		    // Check if 4999 is in the list, if not, add it
		    if (list.get(list.size()-1) != 199)  { list.add(199); }

		    Collections.sort(list); // Re-sort the list

		    // check if head is in the lit, if it is, do nothing
		    if (list.contains(Lists.head)) {} 
		    // but if it is not, then add it
			else {  list.add(Lists.head);  }

			Collections.sort(list); // Re-organize elements in ascending order

			// Before iterating through the list, we will reverse the list
			// In order to iterate from the index of the head, to the reversed end of the list
			Collections.reverse(list);

			//Now we iterate with the reversed conditions met

			for (int i=list.indexOf(Lists.head); i<list.size()-1; i++)
			{
			    absolute_val = Math.abs(list.get(i) - list.get(i+1));
			    distances += absolute_val;
			}

			//So far, we have accomplished to go to one end of the list
			// Since we reversed it, we accomplished going to the end at the beginning, 
			// in this case we went from head to ---> 0

			// Now we do a final iteration to go from 0 to the value at the index before the head
			for (int j=0; j<list.indexOf(Lists.head)-1; j++)
			{
				absolute_val = Math.abs(list.get(j) - list.get(j+1));
				distances += absolute_val;
			}

			Movements_in.cylinders_C_SCAN = distances; // Assign the head movements
			System.out.println("total C_SCAN cylinders = "+ Movements_in.cylinders_C_SCAN);
		} // end of Run()
	} // end of c_scan_thread

	// -------------------------------------------------------------------

		public static class look_thread implements Runnable
	{
        //  Calculate LOOK

        @Override
		public void run() 
		{
			// size of ArrayList for our look list 
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			//list = Lists.main_list;

			// Without the following for loop, we were getting a lot of symmetry problems
			// when attempting to use Collections.sort
			// The issue was:
			/*
			java.lang.IllegalArgumentException: Comparison method violates its general contract!
			*/
			for (int element: Lists.main_list)
			{
				list.add(element); 
			}

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value

		    // check if head is in the lit, if it is, do nothing
		    if (list.contains(Lists.head)) {} 
		    // but if it is not, then add it
			else {  list.add(Lists.head);  }

			Collections.sort(list); // Organize elements in ascending order

			// Before iterating through the list, we will reverse the list
			// In order to iterate from the index of the head, to the reversed end of the list
			Collections.reverse(list);

			//Now we iterate with the reversed conditions met
			for (int i=list.indexOf(Lists.head); i<list.size()-1; i++)
			{
			    absolute_val = Math.abs(list.get(i) - list.get(i+1));
			    distances += absolute_val;
			}

			//So far, we have accomplished to go to one end of the list
			// Since we reversed it, we accomplished going to the end at the beginning, 
			// in this case we went from head to ---> the last element (in reversed form) in the list

			// Very important, the last calculated absolute value from the iteration above
			// did not take into account this absolute value:
			// | the last item from there - the first item at the next iteration |

			// So we assign a temp value to calculate it for us before continuing
			int temp = list.get(list.size()-1);

			// We continue to sort again,
			Collections.sort(list);
			// The purpose is to have the list in order again and to also calculate
			// the distance from temp to the first element from the rest of the list
			// in sorted form (meaning the value after the head)
			distances += Math.abs(temp - list.get(list.indexOf(Lists.head)+1));

			// Then we move into doing our final iteration
			for (int j=list.indexOf(Lists.head)+1; j<list.size()-1; j++)
				{
					absolute_val = Math.abs(list.get(j) - list.get(j+1));
					distances += absolute_val;
				}

			Movements_in.cylinders_LOOK = distances; // Assign the head movements
			System.out.println("total LOOK cylinders = "+ Movements_in.cylinders_LOOK);
		} // end of Run()
	} // end of look_thread

	// -------------------------------------------------------------------

		public static class c_look_thread implements Runnable
	{
        //  Calculate C_LOOK 

        @Override
		public void run() 
		{
			// size of ArrayList for our c-look list
			int limit = 9; 
			//declaring ArrayList with initial size of set limit above
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			//list = Lists.main_list;

			// Without the following for loop, we were getting a lot of symmetry problems
			// when attempting to use Collections.sort
			// The issue was:
			/*
			java.lang.IllegalArgumentException: Comparison method violates its general contract!
			*/
			for (int element: Lists.main_list)
			{
				list.add(element); 
			}

			int distances = 0; // creating an int variable to designate as the sum of the distances
		    int absolute_val = 0;  // Creating an int variable to keep track of the absolute value

		    // check if head is in the lit, if it is, do nothing
		    if (list.contains(Lists.head)) {} 
		    // but if it is not, then add it
			else {  list.add(Lists.head);  }

			Collections.sort(list); // Organize elements in ascending order 

			// Before iterating through the list, we will reverse the list
			// In order to iterate from the index of the head, to the reversed end of the list
			Collections.reverse(list);

			//Now we iterate with the reversed conditions met
			for (int i=list.indexOf(Lists.head); i<list.size()-1; i++)
			{
			    absolute_val = Math.abs(list.get(i) - list.get(i+1));
			    distances += absolute_val;
			}

			//So far, we have accomplished to go to one end of the list
			// Since we reversed it, we accomplished going to the end at the beginning, 
			// in this case we went from head to ---> 0

			// Now we do a final iteration to go from 0 to the value at the index before the head
			for (int j=0; j<list.indexOf(Lists.head)-1; j++)
			{
				absolute_val = Math.abs(list.get(j) - list.get(j+1));
				distances += absolute_val;
			}

			Movements_in.cylinders_C_LOOK = distances; // Assign the head movements
			System.out.println("total C_LOOK cylinders = "+ Movements_in.cylinders_C_LOOK);
		} // end of Run()
	} // end of c_look_thread

	// -------------------------------------------------------------------

} // The end of Class QuestionOne



