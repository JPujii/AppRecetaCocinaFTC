import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import requests
import mtranslate
import random

# Initialize Firebase
cred = credentials.Certificate("C:\\Users\\Josea\\OneDrive\\Escritorio\\TFG\\apprecetascocinaftc-firebase-adminsdk-828rh-1d6be8f2de.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

# Fetch random recipes from Spoonacular
def fetch_random_recipes():
    api_key = "4e582cf7ebaa4b40b0420193d6071f51"
    number_of_recipes = 30
    url = f"https://api.spoonacular.com/recipes/random?apiKey={api_key}&number={number_of_recipes}"
    response = requests.get(url)
    data = response.json()
    return data["recipes"]

# Translate text from English to Spanish
def translate_text(text):
    translated_text = mtranslate.translate(text, 'es', 'auto')
    return translated_text

# Add recipes to Firestore
def add_recipes_to_firestore(recipes):
    recipes_ref = db.collection("recipes")
    recipe_id = 1  # Variable para el ID incremental
    for recipe in recipes:
        translated_title = translate_text(recipe["title"])
        translated_instructions = translate_text(recipe["instructions"])
        
        # Obtener los ingredientes originales
        original_ingredients = [ingredient["original"] for ingredient in recipe["extendedIngredients"]]
        translated_ingredients = translate_text(str(original_ingredients))
        # Unir los ingredientes originales con "\n"

        # Separar los pasos con "-"
        translated_instructions = translated_instructions.replace("\n", " - ")

        recipe_data = {
            "id": recipe_id,
            "nombre": translated_title,
            "tiempo": recipe["readyInMinutes"],
            "pasos": translated_instructions,
            "ingredientes": translated_ingredients
        }
        recipes_ref.document(str(recipe_id)).set(recipe_data)  # Guardar documento con ID
        print(f"Agregada la receta: {translated_title}")
        recipe_id += 1  # Incrementar el ID

# Main function
def main():
    recipes = fetch_random_recipes()
    add_recipes_to_firestore(recipes)

if __name__ == "__main__":
    main()
