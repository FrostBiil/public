// https://stackoverflow.com/questions/6150289/how-can-i-convert-an-image-into-base64-string-using-javascript - besøgt: 08/04-2024
// Denne klasse tilbyder en metode til konvertering af billeder til Base64 streng.
export class ImageConverter {
  /**
   * Konverterer et billede repræsenteret ved en File-instans til en Base64-kodet streng.
   * Denne metode bruger FileReader API'et til at læse billedfilen og konvertere den.
   * 
   * @param file - En File-instans der indeholder billedet der skal konverteres.
   * @returns Promise<string> - En promise der returnerer en Base64-kodet streng når den er opfyldt.
   */
  static convertImageToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader(); // Opretter en FileReader til at læse filen.

      reader.readAsDataURL(file); // Læser filen som en data URL.

      // Håndterer load-begivenhed; kaldes når filen er læst og konverteret til URL.
      reader.onload = () => resolve(reader.result as string);

      // Håndterer errors ved fil-læsning.
      reader.onerror = (error) => reject(error);

      // Log filen til konsol til debugging. (Kan fjernes for produktionsbrug)
      console.log(file);
    });
  }
}

