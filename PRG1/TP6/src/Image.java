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
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
		return false;
	}

	public void affect(Image image2, Dessin dessinWindow) {
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
	}

	public void rotate180(Image image2, Dessin dessinWindow) {
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
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
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
	}

	public void union(Image image1, Image image2, Dessin dessinWindow) {
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
		this.plotImage(3, dessinWindow); // résultat dans fenêtre 3
		image1.plotImage(1, dessinWindow); // première image dans fenêtre 1
		image2.plotImage(2, dessinWindow); // deuxième image dans fenêtre 2
	}

	public boolean testDiagonal() {
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
		return false;
	}

	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
		return false;
	}

	public boolean isIncludedIn(Image image2, Dessin dessinWindow)
	// this est-il inclus dans B
	{
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("------------------------------------------------");
		System.out.println();
		return false;
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