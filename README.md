Online store for sale of fabrics (Stapelok)

Java + Spring Boot

MainPageController - main controller for unauthenticated users;

AdminController - main controller for admins. Include admin panels logics and mapping.;

UserController - main controller for users. Include user office, edit profile, change password logics (user`s authorize requests);

AuthController - login, logout, registration and send 'reser password' email;

SecurityConfig - security configure (HttpSecurity + DaoAuthenticationProvider);

Cart, Order, PreOrder, Products, User - entities;

UserServices - Service for forgot password;

UserDetailsServiceImpl - Service for load user by email if user is exists and return SecurityUser;

SecurityUser - user with simple granted authority

CartRepository, OrderRepository, PreOrderRepository, ProductsRepository, UserRepository - for generic CRUD operations


