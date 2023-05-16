import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import requests
from translate import Translator
import random

# Initialize Firebase
cred = credentials.Certificate("C:\\Users\\Josea\\OneDrive\\Escritorio\\TFG\\apprecetascocinaftc-firebase-adminsdk-828rh-1d6be8f2de.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

# Fetch random recipes from Spoonacular
def fetch_random_recipes():
    api_key = "4e582cf7ebaa4b40b0420193d6071f51"
    number_of_recipes = 5
    url = f"https://api.spoonacular.com/recipes/random?apiKey={api_key}&number={number_of_recipes}"
    response = requests.get(url)
    data = response.json()
    return data["recipes"]

# Translate text from English to Spanish
def translate_text(text):
    translator = Translator(from_lang='en', to_lang='es')
    translated_text = translator.translate(text)
    return translated_text

# Add recipes to Firestore
def add_recipes_to_firestore(recipes):
    recipes_ref = db.collection("recipes")
    for recipe in recipes:
        translated_title = translate_text(recipe["title"])
        translated_instructions = translate_text(recipe["instructions"])
        translated_ingredients = translate_text(str(recipe["extendedIngredients"]))

        recipe_data = {
            "nombre": translated_title,
            "tiempo": recipe["readyInMinutes"],
            "pasos": translated_instructions,
            "ingredientes": translated_ingredients
        }
        recipes_ref.add(recipe_data)
        print(f"Agregada la receta: {translated_title}")

# Main function
def main():
    recipes = fetch_random_recipes()
    add_recipes_to_firestore(recipes)

if __name__ == "__main__":
    main()
