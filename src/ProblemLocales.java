

class ProblemLocales {
    public int hashMapCap;
    public int threads;
    public float loadFactor = (float) 0.75;
    
    static ProblemLocales exampleLocales(){
    	return new ProblemLocales(10_000, 4, 0.75F);
    }
    

    public ProblemLocales(int hashMapCapacity, int numberOfThreads, float hashMapLoadFactor){    
        hashMapCap = hashMapCapacity;
        threads = numberOfThreads;
        loadFactor = hashMapLoadFactor;
    }
}
