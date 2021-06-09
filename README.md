# PC Configuration System

I took inspiration of this project from the Software Development Exam I had in school which was a
group project. Upon seeing the code and running the project we submitted, I was inspired to implement
it differently and possibly add more functionalities. Therefore, I challenged myself to build it from
scratch.

# Features

### Basic

1. Shows which file is opened and whether it is modified or not
2. Search and filter tables
3. Double click table rows to view product details
4. _Added file restriction:_ The admin can only open files containing pc components while customer can 
   only open files containing pc configurations (can be changed easily so that the admin can open both)
5. Save and open *.txt, *.bin, and *.obj files

### Admin

1. Create new pc components
2. Delete multiple components from table
3. Save and open files containing a list of pc components
4. Edit pc components on double click upon table row

### Customer

1. Create custom pc configuration
2. Save custom pc configuration in a file
3. Compare pc components
4. View all "purchased" products upon clicking checkout
5. Save "receipt" when checking out
6. Drag and drop functionality when creating custom pc configurations
7. Add and remove products to cart

### Credentials

A login-system was **not** implemented. Users are simply redirected to the correct
fxml view depending on the "credentials" given.

Log in as an Admin
- **Username**: `admin`
- **Password**: `1234`

Log in as a Customer
- **Username**: `user`
- **Password**: `1234`

# Main View Screenshots
## Login View
![image](https://user-images.githubusercontent.com/56070628/121402988-bf615a00-c95a-11eb-8b70-ca3d1e6fe967.png)
## Admin View
![image](https://user-images.githubusercontent.com/56070628/121403826-99888500-c95b-11eb-91b8-918a21c50e63.png)
## Customer View
![image](https://user-images.githubusercontent.com/56070628/121403589-575f4380-c95b-11eb-9dd4-ab19c3a89bcf.png)
![image](https://user-images.githubusercontent.com/56070628/121403710-7958c600-c95b-11eb-84e9-70c788f360c4.png)

# Popup Screenshots

## Filter Popup view
![image](https://user-images.githubusercontent.com/56070628/121404303-216e8f00-c95c-11eb-9aea-f8039c035706.png)

## Edit Component Popup View

Double clicked on PC Components table row at admin view

![image](https://user-images.githubusercontent.com/56070628/121404853-a9ed2f80-c95c-11eb-9eeb-b07c18b65236.png)

## Component Details Popup View

Double clicked on PC Components table row at customer view

![image](https://user-images.githubusercontent.com/56070628/121406606-a5297b00-c95e-11eb-9f67-945e607be455.png)

Double clicked on PC Components Cart table row 

![image](https://user-images.githubusercontent.com/56070628/121406367-58de3b00-c95e-11eb-8b16-0cbfb9fb3806.png)

Double clicked on PC Configuration Cart table row

![image](https://user-images.githubusercontent.com/56070628/121406927-08b3a880-c95f-11eb-9c7b-c20cbdeef94d.png)

Double clicked on PC Configuration table row at 2nd tab

![image](https://user-images.githubusercontent.com/56070628/121408105-4e24a580-c960-11eb-836c-2004e5058eb6.png)

## Compare Component Popup View
![image](https://user-images.githubusercontent.com/56070628/121407312-72cc4d80-c95f-11eb-9a23-d3053089f7a2.png)

## Checkout Popup View
![image](https://user-images.githubusercontent.com/56070628/121407768-f4bc7680-c95f-11eb-884a-826b7cbde13c.png)

## Custom/New Configuration Popup View

Can drag and drop table row to "Your PC" to add configuration components

![image](https://user-images.githubusercontent.com/56070628/121408557-d30fbf00-c960-11eb-9d10-698d6c77fe78.png)

Alternatively, double click table row to see details and add it to configuration components

![image](https://user-images.githubusercontent.com/56070628/121408947-3ac60a00-c961-11eb-82bd-d3b64502e191.png)

Can double click added component, see its details and remove it

![image](https://user-images.githubusercontent.com/56070628/121409396-bfb12380-c961-11eb-8a86-6c705c9283ed.png)

## Custom Alert Dialogs
![image](https://user-images.githubusercontent.com/56070628/121409721-161e6200-c962-11eb-8e4d-0b1178f0220e.png)
![image](https://user-images.githubusercontent.com/56070628/121409834-351cf400-c962-11eb-9b82-1e33f3efb559.png)
![image](https://user-images.githubusercontent.com/56070628/121409908-48c85a80-c962-11eb-8e68-67caa2e6bcde.png)
![image](https://user-images.githubusercontent.com/56070628/121410055-76ad9f00-c962-11eb-9e36-60264dfa49c3.png)


