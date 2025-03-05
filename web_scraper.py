import alliancepy
import csv
import requests
from bs4 import BeautifulSoup

# Define the API key and get whether to scrape web or from team numbers
scrape = input("Would you like to pull from a event or input team numbers (e/n)")
if scrape == 'e':
    event_key = input("Enter event key: ")
    link = f"https://theorangealliance.org/events/{str(event_key)}"
API_KEY = "0yiFulIXc/n60bxmMPMWA3fRJRCSKx9vqXONFmHF7Vs="
# Function to scrape team numbers from the event page
def scrape_team_numbers(url):
    response = requests.get(url)

    html = response.text  # The HTML content of the page

    soup = BeautifulSoup(html, 'html.parser')

    # Extracting Event Name
    event_name = soup.find('h1', class_='MuiTypography-h1')
    event_name = event_name.text.strip() if event_name else "N/A"

    # Extracting Date
    date_element = soup.find('p', class_='MuiTypography-body1')
    event_date = date_element.text.strip() if date_element else "N/A"

    # Extracting Location
    location_element = soup.find('a', class_='MuiTypography-inherit', href=True)
    event_location = location_element.text.strip() if location_element else "N/A"

    # Extracting Number of Teams
    teams_element = soup.find('button', {'aria-controls': 'radix-:r3:-content-teams'})
    num_teams = teams_element.find('span', class_='MuiBox-root').text if teams_element else "N/A"

    # Extracting Team Numbers
    team_numbers = []
    team_elements = soup.find_all('a', {'href': lambda x: x and '/teams/' in x})  # Find links to team pages

    for team in team_elements:
        team_text = team.text.strip()
        if team_text.isdigit() and team_text not in team_numbers:  # Ensure it's a number
            team_numbers.append(team_text)

    return team_numbers
# Function to get team data
def get_team_data(team_number, season_key, include_long_name):
    try:
        # Create a client object
        client = alliancepy.Client(api_key=API_KEY, application_name="YourAppName")

        # Get the team object
        team = client.team(team_number)

        # Collect team data
        data = [
            team_number,
            team.region,
            team.league,
            team.short_name,
            team.robot_name,
            team.location,
            team.rookie_year,
            team.last_active,
            team.website,
            team.wins,
            team.losses,
            team.ties,
            team.season_wins(season_key),
            team.season_losses(season_key),
            team.season_ties(season_key),
            team.opr(season_key),
            team.np_opr(season_key),
            team.tiebreaker_points(season_key),
            team.ranking_points(season_key),
            team.qualifying_points(season_key),
        ]

        # Optionally include the long name
        if include_long_name:
            data.insert(4, team.long_name)
    except Exception as e:
        print(f"Error fetching data for team {team_number}: {e}. Please try again.")
        return None

    return data

# Function to collect data for multiple teams and save to a CSV file
def collect_and_save_data(team_numbers, season_key, include_long_name, filename):
    # Ensure the filename has the correct extension
    if not filename.endswith('.csv'):
        filename += '.csv'
    
    data_list = []

    for team_number in team_numbers:
        team_data = get_team_data(team_number, season_key, include_long_name)
        if team_data:
            data_list.append(team_data)

    # Write data to CSV file
    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file, delimiter=';')
        
        # Write header
        header = [
            "Category",
            *[f"Team {i + 1}" for i in range(len(team_numbers))]
        ]
        writer.writerow(header)
        
        categories = [
            "Team Number",
            "Region",
            "League",
            "Short Name",
        ]
        if include_long_name:
            categories.append("Long Name")
        categories.extend([
            "Robot Name",
            "Location",
            "Rookie Year",
            "Last Active Season",
            "Website",
            "Total Wins",
            "Total Losses",
            "Total Ties",
            f"Season {season_key} Wins",
            f"Season {season_key} Losses",
            f"Season {season_key} Ties",
            f"Season {season_key} OPR",
            f"Season {season_key} NP_OPR",
            f"Season {season_key} Tiebreaker Points",
            f"Season {season_key} Ranking Points",
            f"Season {season_key} Qualifying Points",
        ])
        
        # Write data for each team
        for idx, category in enumerate(categories):
            row = [category] + [team_data[idx] for team_data in data_list]
            writer.writerow(row)
    
    print(f"Data saved to {filename}")
    print("Remember when you import into Excel that the file is a semicolon-separated CSV file.")

# Scrape team numbers or get them manually
if scrape == 'e':
    team_numbers = scrape_team_numbers(link)
else:
    num_teams = int(input("Enter the number of teams to extract data from: "))
    team_numbers = [int(input(f"Enter team number {i + 1}: ")) for i in range(num_teams)]

# Get the season key, whether to include long name, and filename
season_key = input("Enter the season key (e.g., '2425'): ")
include_long_name = input("Would you like to include the long name? (y/n): ").strip().lower() == 'y'
filename = input("Enter the filename for the CSV file (e.g., 'team_data.csv'): ")

# Save data to CSV file
collect_and_save_data(team_numbers, season_key, include_long_name, filename)