import os
import psycopg

# Establish a connection to the database
DB_HOST = '???'
DB_PORT = '???'
DB_NAME = '???'
DB_USER = '???'
DB_PASSWORD = '???'

# Establish a connection to the database
conn = psycopg.connect(
    host=DB_HOST,
    port=DB_PORT,
    dbname=DB_NAME,
    user=DB_USER,
    password=DB_PASSWORD
)
cursor = conn.cursor()

# Function to insert author and get the author_id


def insert_author(author_name):
    cursor.execute(
        "INSERT INTO public.author (name) VALUES (%s) RETURNING id", (author_name,))
    author_id = cursor.fetchone()[0]
    conn.commit()
    return author_id

# Function to insert a song into the 'song' table


def truncate_at_author(input_string, author_name):
    # Replace any dashes with spaces in the author's name
    author_name_cleaned = author_name.replace('-', ' ')

    # Search for the starting point of the author's name in the input string
    author_index = input_string.lower().find(author_name_cleaned.lower())

    if author_index != -1:
        # If the author's name is found, return the string before it
        result = input_string[:author_index].strip()
    else:
        # If the author's name is not found, return the original string
        result = input_string

    return result


def insert_song(title, english_lyrics, hindi_lyrics, urdu_lyrics, author_id):
    cursor.execute("""
        INSERT INTO public.song (title, english_lyrics, hindi_lyrics, urdu_lyrics, author_id)
        VALUES (%s, %s, %s, %s, %s)
    """, (title, english_lyrics, hindi_lyrics, urdu_lyrics, author_id))
    conn.commit()


# Path to the extracted dataset
dataset_path = 'dataset'  # Change this to your actual path

c = 0
# Loop through authors in the dataset directory
for author in os.listdir(dataset_path):
    print("1. Now Writing ", (author.replace('-', ' ')))
    author_dir = os.path.join(dataset_path, author)

    # Skip system files
    if not os.path.isdir(author_dir):
        continue

    # Insert author into the database and get author_id
    author_id = insert_author(author.replace('-', ' '))

    # Loop through the songs for this author (assuming directories 'en', 'hi', 'ur' exist)
    english_dir = os.path.join(author_dir, 'en')
    hindi_dir = os.path.join(author_dir, 'hi')
    urdu_dir = os.path.join(author_dir, 'ur')

    for song_file in os.listdir(english_dir):
        c += 1
        title = song_file.replace('-', ' ').split('.txt')[0]
        print("  2. Now Writing ", (truncate_at_author(title, author)))
        # Read the lyrics in each language
        with open(os.path.join(english_dir, song_file), 'r', encoding='utf-8') as f:
            english_lyrics = f.read()
            print("    3a.Now Writing english")

        hindi_lyrics = ''
        urdu_lyrics = ''

        hindi_file_path = os.path.join(hindi_dir, song_file)
        urdu_file_path = os.path.join(urdu_dir, song_file)

        if os.path.exists(hindi_file_path):
            with open(hindi_file_path, 'r', encoding='utf-8') as f:
                hindi_lyrics = f.read()
                print("    3b.Now Writing hindi")

        if os.path.exists(urdu_file_path):
            with open(urdu_file_path, 'r', encoding='utf-8') as f:
                urdu_lyrics = f.read()
                print("    3c.Now Writing urdu")
        # Insert the song data into the database
        insert_song(truncate_at_author(title, author), english_lyrics, hindi_lyrics,
                    urdu_lyrics, author_id)

print(c, " songs loaded")
# Close the cursor and connection
cursor.close()
conn.close()
