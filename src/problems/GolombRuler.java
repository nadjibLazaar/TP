package problems;

import org.kohsuke.args4j.Option;
import problems.AbstractProblem;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;


public class GolombRuler extends AbstractProblem {

    @Option(name = "-m", usage = "Golomb ruler order.", required = false)
    private int m = 10;
    private IntVar[] vars;
    

    @Override
    public void buildModel() {
    	// Build Model here. 
    	model = new Model();
    	vars = new IntVar[m];
    	for (int i = 0; i<m; i++) {
    		vars[i]= model.intVar("marqueur_"+(i+1), 0, m*m);
    	}
    	/*
    	for(int i = 0; i<m; i++ ) {
    		for(int j = 0; j<m; j++ ) {
    			for(int k = 0; k<m; k++ ) {
    				for(int l = 0; l<m; l++ ) {
    					{ if ((j>i) && (l>k) &&((i!=k)||(j!=l))) {
    						
	    						double[] coeffs = new double[]{-1, 1, -1, 1};
	    						model.scalar( new IntVar[] {vars[i], vars[j], vars[l], vars[k]}, coeffs, "!=", 0).post();
    						}
    					}
    				}
    			}
    		}
    	}
    	*/
	    
	for(int i = 0; i<m-1; i++ ) 
    		for(int j = i+1; j<m; j++ ) 
    			for(int k = 0; k<m-1 && i!=k; k++ ) 
    				for(int l = k+1; l<m && j!=l; l++ ) 
	    				model.scalar( new IntVar[] {vars[i], vars[j], vars[l], vars[k]}, 
						     new int[]{-1, 1, -1, 1}, "!=", 0).post();
    	
    	
    	for (int i=0; i<m-1; i++) 
    		model.arithm(vars[i], "<", vars[i+1]).post();
	    
	    
    	model.arithm(vars[0], "=", 0).post();
    	
    }

    @Override
    public void configureSearch() {
    	
    	// Set search here
    }

    @Override
    
    public void solve() {
    	Solution solution = model.getSolver().findSolution();
    	if(solution != null){
    	    System.out.println(solution.toString());
    	}
    	else {System.out.println("pas de solution, cherche l'erreur");}
    	// Set objective if needed;
    	// Solve the instance
    	// Print the solution
        
    }

    public static void main(String[] args) {
        new GolombRuler().execute(args);
    }
}

