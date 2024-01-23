// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		//print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		//Color[][] scaledImage = scaled(tinypic, 3, 5);
		//imageOut = scaled(tinypic);
		//System.out.println();
		//print(imageOut);
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	
		// Print the original image
		print(tinypic);

		// Scale the image to a new size
		imageOut = scaled(tinypic, 3, 5);

		// Print the resulting scaled image
		System.out.println();
		print(imageOut);

	}

	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
	
		// Reads the RGB values from the file, into the image array.
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int red = in.readInt();
				int green = in.readInt();
				int blue = in.readInt();
				image[i][j] = new Color(red, green, blue);
			}
		}
	
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
			for (Color[] row : image) {
				for (Color pixel : row) {
					print(pixel);
				}
				System.out.println(); // Move to the next line after each row
			}
		}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
	int numRows = image.length;
    int numCols = image[0].length;

    // Create a new image with the same dimensions as the original
    Color[][] flippedImage = new Color[numRows][numCols];

    // Populate the flippedImage by copying pixels horizontally flipped from the original image
    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            // Flip horizontally by reversing the order of pixels in each row
            flippedImage[i][j] = image[i][numCols - 1 - j];
        }
    }

    return flippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
	//// Replace the following statement with your code
	int numRows = image.length;
    int numCols = image[0].length;

    // Create a new image with the same dimensions as the original
    Color[][] flippedImage = new Color[numRows][numCols];

    // Populate the flippedImage by copying pixels vertically flipped from the original image
    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            // Flip vertically by reversing the order of pixels in each column
            flippedImage[i][j] = image[numRows - 1 - i][j];
        }
    }

    return flippedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
	
		// Compute luminance using the formula
		int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
	
		// Create a Color object with the computed luminance for each channel
		return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		//// Replace the following statement with your code
		int numRows = image.length;
    	int numCols = image[0].length;
		Color[][] grayImage = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grayImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int originalWidth = image[0].length;
		int originalHeight = image.length;
	
		// Create a new image with the specified width and height
		Color[][] scaledImage = new Color[height][width];
	
		// Calculate scale factors
		double widthScale = (double) originalWidth / width;
		double heightScale = (double) originalHeight / height;
	
		// Iterate through each pixel in the scaled image
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Calculate corresponding pixel coordinates in the original image
				int originalX = (int) (j * widthScale);
				int originalY = (int) (i * heightScale);
	
				// Set the pixel in the scaled image to the corresponding pixel in the original image
				scaledImage[i][j] = image[originalY][originalX];
			}
		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r1 = c1.getRed();
		int g1 = c1.getGreen();
		int b1 = c1.getBlue();
	
		int r2 = c2.getRed();
		int g2 = c2.getGreen();
		int b2 = c2.getBlue();
	
		// Calculate the blended color components
		int blendedR = (int) (alpha * r1 + (1 - alpha) * r2);
		int blendedG = (int) (alpha * g1 + (1 - alpha) * g2);
		int blendedB = (int) (alpha * b1 + (1 - alpha) * b2);
	
		// Create and return the blended color
		return new Color(blendedR, blendedG, blendedB);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int numRows = image1.length;
		int numCols = image1[0].length;
	
		// Create a new image with the same dimensions
		Color[][] blendedImage = new Color[numRows][numCols];
	
		// Iterate through each pixel in the images
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				// Blend the corresponding pixels from image1 and image2
				blendedImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blendedImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		// Scale the target image to the dimensions of the source image
		Color[][] scaledTarget = scaled(target, source[0].length, source.length);
	
		// Iterate through the morphing steps
		for (int i = 0; i <= n; i++) {
			// Calculate alpha for blending
			double alpha = (double) (n - i) / n;
	
			// Blend the source and scaled target images
			Color[][] morphedImage = blend(source, scaledTarget, alpha);
	
			// Display the morphed image
			Runigram.display(morphedImage);
	
			// Pause for about 500 milliseconds
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

