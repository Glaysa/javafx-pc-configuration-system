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

# Screenshots