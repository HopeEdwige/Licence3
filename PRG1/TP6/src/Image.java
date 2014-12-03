import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Image extends BinaryTree<NodeState> {
	
	private static final Scanner standardInput = new Scanner(System.in);
	private static final int WINDOW_SIZE = 256;

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}


	public boolean isPixelOn(int x, int y) {
		Iterator<NodeState> it = this.iterator();
		int tmpX = 256, tmpY = 256;
		
		while (!it.isEmpty()) {
			
			//Horizontal
			if (tmpX == tmpY) {
				if (x < (tmpX/2))
					it.goLeft();  //Upper
				
				else
					it.goRight();  //Lower
				
				tmpX = tmpX/2;
			}
			
			//Vertical
			else {
				if (y < (tmpY/2))
					it.goLeft();  //Left
				
				else
					it.goRight();  //Right
				
				tmpY = tmpY/2;
			}
		}
		
		it.goUp();
		return (it.getValue().equals(NodeState.valueOf(1)));
	}

	public void affect(Image image2, Dessin dessinWindow) {
		Iterator<NodeState> it = image2.iterator();
		Iterator<NodeState> itThis = this.iterator();
		
		itThis.clear();
		
		if (!it.isEmpty())
			affectAux(it, itThis);
		
		//Display on the first screen
		this.plotImage(3, dessinWindow);
	}
	
	
	private void affectAux(Iterator<NodeState> it, Iterator<NodeState> itThis) {
		if (!itThis.isEmpty()) {
			itThis.addValue(it.getValue().copy());
			
			it.goLeft();
			itThis.goLeft();
			affectAux(it, itThis);
			it.goUp();
			itThis.goUp();
			it.goRight();
			itThis.goRight();
			affectAux(it, itThis);
			it.goUp();
			itThis.goUp();
		}
	}
	

	public void rotate180(Image image2, Dessin dessinWindow) {
		Iterator<NodeState> it = image2.iterator();
		Iterator<NodeState> itThis = this.iterator();
		
		//Clear this image
		itThis.clear();
		
		if (!it.isEmpty())
			rotateAux(it, itThis);
		
		this.plotImage(2, dessinWindow);
	}
	
	private void rotateAux (Iterator<NodeState> it, Iterator<NodeState> itThis) {
		if (!it.isEmpty()) {
			int e = it.getValue().e;
			itThis.addValue(NodeState.valueOf(e));
			it.goLeft();
			itThis.goRight();
			rotateAux(it, itThis);
			it.goUp();
			itThis.goUp();
			it.goRight();
			itThis.goLeft();
			rotateAux(it, itThis);
			it.goUp();
			itThis.goUp();
		}
	}
	
	public void videoInverse(Dessin dessinWindow) {
		//Draw the drawing as beginning in the 1st window
		plotImage(1, dessinWindow);
		
		//Get an iterator
		Iterator<NodeState> it = this.iterator();
		
		//Throw the invert
		invertAux(it);
		
		//Draw the drawing as result in the 2nd window
		plotImage(2, dessinWindow);
	}
	
	
	private void invertAux(Iterator<NodeState> it) {
		//If not empty
		if (!it.isEmpty()) {
			NodeState ns = it.getValue();
			
			//Process this one
			if (ns.e == 1) {
				it.setValue(NodeState.valueOf(0));
			}
			else if (ns.e == 0) {
				it.setValue(NodeState.valueOf(1));
			}
			
			//Process the sons
			it.goLeft();
			invertAux(it);
			it.goUp();
			it.goRight();
			invertAux(it);
			it.goUp();
		}
	}

	public void intersection(Image image1, Image image2, Dessin dessinWindow) {
		//Get iterators
		Iterator<NodeState> it1 = image1.iterator();
		Iterator<NodeState> it2 = image2.iterator();
		Iterator<NodeState> it3 = this.iterator();
		
		//Clear this image
		it3.clear();
		
		if (!it1.isEmpty() && !it2.isEmpty()){
			intersectionAux(it1, it2, it3);
		}
		
		this.plotImage(3, dessinWindow); // résultat dans fenêtre 3
		image1.plotImage(1, dessinWindow); // première image dans fenêtre 1
		image2.plotImage(2, dessinWindow); // deuxième image dans fenêtre 2
	}
	
	private void intersectionAux(Iterator<NodeState> it1, Iterator<NodeState> it2, Iterator<NodeState> itThis) {
		if (!it1.isEmpty() && !it2.isEmpty()){
			NodeState ns1 = it1.getValue();
			NodeState ns2 = it2.getValue();
			
			if (ns1.equals(NodeState.valueOf(0)) || ns2.equals(NodeState.valueOf(0))) {
				itThis.addValue(NodeState.valueOf(0));
			}
			else if (ns1.equals(NodeState.valueOf(1)) && ns2.equals(NodeState.valueOf(1))) {
				itThis.addValue(NodeState.valueOf(1));
			}
			else if (ns1.equals(NodeState.valueOf(2)) && ns2.equals(NodeState.valueOf(2))) {
				itThis.addValue(NodeState.valueOf(2));
			}
			else {
				itThis.addValue(NodeState.valueOf(2));
				if (ns1.equals(NodeState.valueOf(1))) {
					it1.goLeft();
					it1.addValue(NodeState.valueOf(1));
					it1.goUp();
					it1.goRight();
					it1.addValue(NodeState.valueOf(1));
					it1.goUp();
				} else {
					it2.goLeft();
					it2.addValue(NodeState.valueOf(1));
					it2.goUp();
					it2.goRight();
					it2.addValue(NodeState.valueOf(1));
					it2.goUp();
				}
			}
			
			it1.goLeft();
			it2.goLeft();
			itThis.goLeft();
			intersectionAux(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();
			it1.goRight();
			it2.goRight();
			itThis.goRight();
			intersectionAux(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();
			
		}
	}

	public void union(Image image1, Image image2, Dessin dessinWindow) {
		Iterator<NodeState> it1 = image1.iterator();
		Iterator<NodeState> it2 = image2.iterator();
		Iterator<NodeState> it3 = this.iterator();
		
		//Clear this image
		it3.clear();
		
		if (!it1.isEmpty() && !it2.isEmpty()){
			unionAux(it1, it2, it3);
		}
		this.plotImage(3, dessinWindow); // résultat dans fenêtre 3
		image1.plotImage(1, dessinWindow); // première image dans fenêtre 1
		image2.plotImage(2, dessinWindow); // deuxième image dans fenêtre 2
	}
	
	private void unionAux (Iterator<NodeState> it1, Iterator<NodeState> it2, Iterator<NodeState> itThis) {
		if (!it1.isEmpty() && !it2.isEmpty()){
			NodeState ns1 = it1.getValue();
			NodeState ns2 = it2.getValue();
			
			if (ns1.equals(NodeState.valueOf(1)) || ns2.equals(NodeState.valueOf(1))) {
				itThis.addValue(NodeState.valueOf(1));
			}
			else if (ns1.equals(NodeState.valueOf(0)) && ns2.equals(NodeState.valueOf(0))) {
				itThis.addValue(NodeState.valueOf(0));
			}
			else if (ns1.equals(NodeState.valueOf(2)) && ns2.equals(NodeState.valueOf(2))) {
				itThis.addValue(NodeState.valueOf(2));
			}
			else {
				itThis.addValue(NodeState.valueOf(2));
				if (ns1.equals(NodeState.valueOf(0))) {
					it1.goLeft();
					it1.addValue(NodeState.valueOf(0));
					it1.goUp();
					it1.goRight();
					it1.addValue(NodeState.valueOf(0));
					it1.goUp();
				} else {
					it2.goLeft();
					it2.addValue(NodeState.valueOf(0));
					it2.goUp();
					it2.goRight();
					it2.addValue(NodeState.valueOf(0));
					it2.goUp();
				}
			}
			
			it1.goLeft();
			it2.goLeft();
			itThis.goLeft();
			unionAux(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();
			it1.goRight();
			it2.goRight();
			itThis.goRight();
			unionAux(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();
			
		}
	}

	public boolean testDiagonal() {
		Iterator<NodeState> it = this.iterator();

		if (!it.isEmpty()) {
			return testDiagAux(it);
		} else {
			System.out.println("Image vide!");
			return false;
		}
	}
	
	private boolean testDiagAux(Iterator<NodeState> it) {
		boolean result = true;
		if (!it.isEmpty()) {
			if (it.getValue().equals(NodeState.valueOf(0))) {
				result = false;
			} 
			
			else if (it.getValue().equals(NodeState.valueOf(2))) {
				
				//Go to Left 1st generation
				it.goLeft();
				
				if (it.getValue().equals(NodeState.valueOf(1))) {
					//Go Up 1st generation
					it.goUp();
					//Go to Right 1st generation
					it.goRight();
					if (it.getValue().equals(NodeState.valueOf(0))) {
						result = false;
					} else if (it.getValue().equals(NodeState.valueOf(2))) {
						// go to Right 2nd generation
						it.goRight();
						if (!testDiagAux(it)) {
							result = false;
						}
						it.goUp();
					}
					it.goUp();
				} else if (it.getValue().equals(NodeState.valueOf(0))) {
					result = false;
				} else {
					it.goLeft();
					if (!testDiagAux(it)) {
						result = false;
					}
					it.goUp();
				}
				it.goUp();
			}
		}
		return result;
	}

	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		boolean result = true;

		Iterator<NodeState> it = this.iterator();
		int tmpX = 256, tmpY = 256;
		
		while ((!it.isEmpty()) && (result)) {
			
			//Horizontal
			if (tmpX == tmpY) {
				if ((x1 < (tmpX/2)) && (x2 < (tmpX/2))) {
					it.goLeft();  //Upper
				}
				else if ((x1 > (tmpX/2)) && (x2 > (tmpX/2))) {
					it.goRight();  //Lower
				}
				else {
					result = false;
				}

				tmpX = tmpX/2;
			}
			
			//Vertical
			else {
				if (result) {
					if ((y1 < (tmpY/2)) && (y2 < (tmpY/2))) {
						it.goLeft();  //Left
					}
					else if ((y1 > (tmpY/2)) && (y2 > (tmpY/2))) {
						it.goRight();  //Right
					} else {
						result = false;
					}
					
					tmpY = tmpY/2;
				}
			}
		}
		
		return result;
	}

	public boolean isIncludedIn(Image image2, Dessin dessinWindow)
	// this est-il inclus dans B
	{
		Iterator<NodeState> itThis = this.iterator();
		Iterator<NodeState> it2 = image2.iterator();
		if (it2.isEmpty()) {
			System.out.println("Image2 vide!");
			return false;
		} else {
			return isIncluAux(itThis, it2);
		}
	}
	
	private boolean isIncluAux (Iterator<NodeState> itThis, Iterator<NodeState> it2) {
		boolean result = true;
		if (!it2.isEmpty()) {
			if (!itThis.isEmpty()) {
				if (it2.getValue().equals(NodeState.valueOf(1)) || itThis.getValue().equals(NodeState.valueOf(0))) {
					//On ne fait rien
				} else if (it2.getValue().equals(NodeState.valueOf(0)) || itThis.getValue().equals(NodeState.valueOf(1))) {
					result = false;
				} else {
					itThis.goLeft();
					it2.goLeft();
					if (isIncluAux(itThis, it2)) {
						itThis.goUp();
						it2.goUp();
						itThis.goRight();
						it2.goRight();
						if (!isIncluAux(itThis, it2)) {
							result = false;
						}
					} else {
						result = false;
					}
					itThis.goUp();
					it2.goUp();
				}
			}
		}
		return result;
	}
	
	public static void readImage(int windowNumber, Dessin dessinWindow) {
		System.out.print("nom du fichier d'entree : ");
		String fileName = standardInput.nextLine();
		InputStream inFile;
		try {
			inFile = new FileInputStream(fileName);
			System.out.println("Corrigé : readImage");
			System.out.println("---------------------");
			xReadImageFromFile(inFile, windowNumber, dessinWindow);
			inFile.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("fichier " + fileName + " inexistant");
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("impossible de fermer le fichier " + fileName);
			System.exit(0);
		}
	}

	protected static void xReadImageFromFile(InputStream is, int windowNumber,
			Dessin dessinWindow) {
		Scanner scanner = (is == System.in) ? standardInput : new Scanner(is);
		int e, x1, x2, y1, y2;
		dessinWindow.clearWindow(windowNumber);
		e = scanner.nextInt();// Lecture.lireInt(fileHandle);
		while (e != -1) {
			x1 = scanner.nextInt();
			y1 = scanner.nextInt();
			x2 = scanner.nextInt();
			y2 = scanner.nextInt();
			switch (e) {
			case 0:
				dessinWindow.switchOff(x1, y1, x2, y2, windowNumber);
				break;
			case 1:
				dessinWindow.switchOn(x1, y1, x2, y2, windowNumber);
				break;
			}
			e = scanner.nextInt();
		}
		if (is != System.in) {
			scanner.close();
		}
	}

	public void constructTree(int windowNumber, Dessin dessinWindow) {
		Iterator<NodeState> it = this.iterator();
		it.clear();
		xConstructTree(it, 0, 0, WINDOW_SIZE, true, windowNumber, dessinWindow);
	}

	private static void xConstructTree(Iterator<NodeState> it, int x, int y,
			int squareWidth, boolean isSquare, int windowNumber,
			Dessin dessinWindow) {
		int rectangleWidth = squareWidth / 2;
		int v;
		if (isSquare) {
			v = dessinWindow.state(x, y, x + squareWidth - 1, y + squareWidth
					- 1, windowNumber);
		}
		else {
			v = dessinWindow.state(x, y, x + squareWidth - 1, y
					+ rectangleWidth - 1, windowNumber);
		}
		it.addValue(NodeState.valueOf(v));
		if (v == 2) {
			it.goLeft();
			if (isSquare) {
				xConstructTree(it, x, y, squareWidth, false, windowNumber,
						dessinWindow);
			} 
			else {
				xConstructTree(it, x, y, rectangleWidth, true, windowNumber,
						dessinWindow);
			}
			it.goUp();
			it.goRight();
			if (isSquare) {
				xConstructTree(it, x, y + rectangleWidth, squareWidth, false,
						windowNumber, dessinWindow);
			} 
			else {
				xConstructTree(it, x + rectangleWidth, y, rectangleWidth, true,
						windowNumber, dessinWindow);
			}
			it.goUp();
		}
	}

	public void save() {
		System.out.print("nom du fichier de sortie : ");
		String fileName = standardInput.next();// Lecture.lireString();
		OutputStream outFile;
		try {
			outFile = new FileOutputStream(fileName);
			System.out.println("Corrigé : Save");
			System.out.println("----------------");
			Iterator<NodeState> it = this.iterator();
			String ch = xSave(it, 0, 0, WINDOW_SIZE, true) + "-1\n";
			outFile.write(ch.getBytes());
			outFile.flush();
			outFile.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("problème d'ouverture de fichier pour "
					+ fileName);
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("impossible de fermer le fichier " + fileName);
			System.exit(0);
		}
	}

	private static String xSave(Iterator<NodeState> it, int x, int y,
			int squareWidth, boolean isSquare) {
		int rectangleWidth = squareWidth / 2;
		NodeState node;
		String result = "";
		switch (it.nodeType()) {
		case LEAF:
			node = it.getValue();
			if (isSquare) {
				if (node.e == 1) {
					result = result + "1 " + x + " " + y + " "
							+ (x + squareWidth - 1) + " "
							+ (y + squareWidth - 1) + "\n";
				}
			} 
			else if (node.e == 1) {
				result = result + "1 " + x + " " + y + " "
						+ (x + squareWidth - 1) + " "
						+ (y + rectangleWidth - 1) + "\n";
			}
			break;
		case DOUBLE:
			it.goLeft();
			if (isSquare) {
				result += xSave(it, x, y, squareWidth, false);
			}
			else {
				result += xSave(it, x, y, rectangleWidth, true);
			}
			it.goUp();
			it.goRight();
			if (isSquare) {
				result += xSave(it, x, y + rectangleWidth, squareWidth, false);
			} 
			else {
				result += xSave(it, x + rectangleWidth, y, rectangleWidth, true);
			}
			it.goUp();
			break;
		}
		return result;
	}

	public void plotTree() {
		TreeFrame fa = new TreeFrame(this);
		fa.setVisible(true);
	}

	public void plotImage(int windowNumber, Dessin dessinWindow) {
		dessinWindow.clearWindow(windowNumber);
		Iterator<NodeState> it = this.iterator();
		xPlotTree(it, 0, 0, WINDOW_SIZE, true, windowNumber, dessinWindow);
	}

	private static void xPlotTree(Iterator<NodeState> it, int x, int y,
			int squareWidth, boolean isSquare, int windowNumber,
			Dessin dessinWindow) {
		int rectangleWidth = squareWidth / 2;
		NodeState node;
		switch (it.nodeType()) {
		case LEAF:
			node = it.getValue();
			if (isSquare) {
				if (node.e == 1) {
					dessinWindow.switchOn(x, y, x + squareWidth - 1, y
							+ squareWidth - 1, windowNumber);
				}
			} 
			else {
				if (node.e == 1) {
					dessinWindow.switchOn(x, y, x + squareWidth - 1, y
							+ rectangleWidth - 1, windowNumber);
				}
			}
			break;
		case DOUBLE:
			it.goLeft();
			if (isSquare) {
				xPlotTree(it, x, y, squareWidth, false, windowNumber,
						dessinWindow);
			} 
			else {
				xPlotTree(it, x, y, rectangleWidth, true, windowNumber,
						dessinWindow);
			}
			it.goUp();
			it.goRight();
			if (isSquare) {
				xPlotTree(it, x, y + rectangleWidth, squareWidth, false,
						windowNumber, dessinWindow);
			} 
			else {
				xPlotTree(it, x + rectangleWidth, y, rectangleWidth, true,
						windowNumber, dessinWindow);
			}
			it.goUp();
			break;
		}
	}

	public int height() {
		System.out.println("Corrigé : Height");
		System.out.println("-----------------");
		return xHeight(this.iterator());
	}

	protected static int xHeight(Iterator<NodeState> it) {
		NodeType type = it.nodeType();
		assert type == NodeType.LEAF || type == NodeType.DOUBLE : "l'arbre comporte des noeuds simples";
		int leftHeight = 0;
		int rightHeight = 0;
		switch (type) {
		case LEAF:
			return 0;
		case DOUBLE:
			it.goLeft();
			leftHeight = xHeight(it);
			it.goUp();
			it.goRight();
			rightHeight = xHeight(it);
			it.goUp();
		default: /* impossible */
		}
		return (leftHeight > rightHeight) ? leftHeight + 1 : rightHeight + 1;
	}

	public int numberOfNodes() {
		System.out.println("Corrigé : numbreOfNodes");
		System.out.println("-----------------");
		return xNumberOfNodes(this.iterator());
	}

	protected static int xNumberOfNodes(Iterator<NodeState> it) {
		NodeType type = it.nodeType();
		assert type == NodeType.LEAF || type == NodeType.DOUBLE : "l'arbre comporte des noeuds simples";
		assert (type != NodeType.SENTINEL) : "l'itérateur est sur le butoir";
		int number = 0;
		switch (type) {
		case LEAF:
			return 1;
		case DOUBLE:
			it.goLeft();
			number = xNumberOfNodes(it);
			it.goUp();
			it.goRight();
			number += xNumberOfNodes(it);
			it.goUp();
		default: /* impossible */
		}
		return number + 1;
	}

}