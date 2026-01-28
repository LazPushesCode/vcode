import java.util.ArrayList;
public class Entity {
    double[][] objectSpaceVectors;
    int [][] indices;

    double[][] worldSpaceVectors;

    ArrayList<double[]> viewSpaceVectors;
    ArrayList<double[]> finalVectors;
    ArrayList<int[]> finalIndices;


    Matrix transformation;

    Entity(double [][] vertices, int [][] ind, Matrix m){
        objectSpaceVectors = new double[vertices.length][vertices[0].length+1];
        this.indices = ind;
        for(int i = 0; i < vertices.length; i++){
            for(int j = 0; j < vertices[i].length; j++){
                objectSpaceVectors[i][j] = vertices[i][j];
            }
            objectSpaceVectors[i][3] = 1;
        }
        this.transformation = m;
        worldSpaceVectors = new double[vertices.length][vertices[0].length+1];
        viewSpaceVectors = new ArrayList<>();
        finalVectors = new ArrayList<>();
        finalIndices = new ArrayList<>();
    }
    void convertToWorldSpace(){
        for(int k = 0; k < objectSpaceVectors.length; k++){
            for(int i = 0; i < transformation.m.length; i++){
                worldSpaceVectors[k][i] = 0;
                for(int j = 0; j < transformation.m[i].length; j++){
                    worldSpaceVectors[k][i] += transformation.m[i][j] * objectSpaceVectors[k][j];
                }
            }
        }
    }
    void transformEntity(int[] translate, int[] scale, char axis, int degree){
        transformation = Matrix.translate(translate[0], translate[1], translate[2])
        .multiply(Matrix.scale(scale[0], scale[1], scale[2]));
        switch (axis) {
            case 'x':
                transformation.multiply(Matrix.rotatex(degree));
                break;
            case 'y':
                transformation.multiply(Matrix.rotatey(degree));
                break;
            case 'z':
                transformation.multiply(Matrix.rotatez(degree));
                break;
            default:
                throw new AssertionError();
        }
    }
    void sortVertices(){
        for(int i = 0; i < finalIndices.size(); i++){
            for(int j = 0; j < finalIndices.get(i).length; j++){
                for(int k = j+1; k < finalIndices.get(i).length; k++){
                    int jPosition = finalIndices.get(i)[j];
                    int kPosition = finalIndices.get(i)[k];
                    if(finalVectors.get(jPosition)[1] > finalVectors.get(kPosition)[1]){
                        swapVertices(i, j, k);
                    } else if(finalVectors.get(jPosition)[1] == finalVectors.get(kPosition)[1]){
                        if(finalVectors.get(jPosition)[0] < finalVectors.get(kPosition)[0]) {
                           swapVertices(i, j, k);
                        }
                    }
                }
            }
        }
        
    }
    void swapVertices(int row, int a, int b){
        int temp = finalIndices.get(row)[a];
        finalIndices.get(row)[a] = finalIndices.get(row)[b];
        finalIndices.get(row)[b] = temp;
    }
    void invertObject(){
        for(int i = 0; i < objectSpaceVectors.length; i++){
            objectSpaceVectors[i][1] *= -1;
        }
    }
    void resetFrameData(){
        viewSpaceVectors.clear();
        finalVectors.clear();
        finalIndices.clear();
    }
}
