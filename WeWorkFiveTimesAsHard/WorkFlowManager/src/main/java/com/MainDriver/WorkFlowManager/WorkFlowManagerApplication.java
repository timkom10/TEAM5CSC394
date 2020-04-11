package com.MainDriver.WorkFlowManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*NO @DATA => dont have to get & set (among other things) for this class*/
public class WorkFlowManagerApplication {

	/*On the first logic pass through, a static is == 0, so it is EXIT_SUCCESS*/
	private static MAIN_THREAD_STATUS mStatus;

	public static void main(String[] args)
	{
		SpringApplication.run(WorkFlowManagerApplication.class, args);

		/*In general, try to have as few news as possible, keep in mind for later*/
		LombokDemonstration LD = new LombokDemonstration();
		LD.setNotSet(44); //its 44 (+1) now

		/*No stack wasting exceptions, faster, and more manageable (Long term)*/
		mStatus = CheckSet(LD);

		/*After critical operation, check status*/
		VerifyThreadStatus();

		System.out.println("No errors?");
	}

	/*
		Checks whether or not the value is really 45
	 */
	private static MAIN_THREAD_STATUS CheckSet(LombokDemonstration obj)
	{
		if(obj.getNotSet() != 45)
		{
			return MAIN_THREAD_STATUS.FAILEDCHECKSUM; //1
		}
		return MAIN_THREAD_STATUS.EXITSUCCESS; //0
	}

	/*
		Checks whether or not the calling thread operating on this object, is in an error state
	 */
	private static void VerifyThreadStatus()
	{
		if(mStatus != MAIN_THREAD_STATUS.EXITSUCCESS)
		{
			/*PRINT, EXIT (only since we know this is the main thread)*/
			printERROR(mStatus);
			System.exit(1);
		}
	}

	/*
		Prints the associated error message with each possible error state
	 */
	private static void printERROR(MAIN_THREAD_STATUS err)
	{
		/*When it gets to be big (and it will be) this is much faster than ifs and exceptions*/
		switch (err)
		{
			case EXITSUCCESS:
			{
				System.out.println("Somehow, an error was reported for an exit success at line number");
				break;
			}
			case FAILEDCHECKSUM:
			{
				System.out.println("Failed to verify set");
				break;
			}
			default:
				break;
		}
	}

	/*
	Gets the current line number of where something failed...

	Too slow, fuck it. Error messages should be obvious as to where they are failing, plus,
	Exposing where something failed, brings with it, its own issues

	private static int LineNumberWhereErrorOccurred()
	{
		return new Throwable().getStackTrace()[0].getLineNumber();
	}
	*/
}
