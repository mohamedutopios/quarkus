# Start from a base Python 3.9 image
FROM python:3.9-slim

# Install xmltodict
RUN pip install xmltodict

# Create a directory to hold the script
WORKDIR /app


USER 1000

# Copy the script into the image
COPY convert_xml_to_json.py .

# Set the script as the command to run when the container starts
CMD ["python", "./convert_xml_to_json.py"]
