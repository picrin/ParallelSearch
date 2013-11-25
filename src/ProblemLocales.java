

class ProblemLocales {
    public int hashMapSize;
    public int hashMapCap;
    public int threads;
    public float loadFactor = (float) 0.75;

    public static int sizeToCap(int size, float loadFactor){
        return (int) ((size/(loadFactor - 0.01)) + 1);
    }

    public ProblemLocales(int hashMapSize, int numberOfThreads, float hashMapLoadFactor){    
        hashMapCap = sizeToCap(hashMapSize, hashMapLoadFactor);
        this.hashMapSize = hashMapSize;
        threads = numberOfThreads;
        loadFactor = hashMapLoadFactor;
    }
}
