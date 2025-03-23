
export class User {
    username: string;
    roles: string[];
    email: string;
    phone: string;
    firstName: string;
    lastName: string;
    constructor(username: string, roles: string[], email: string, phone: string, firstName: string, lastName: string) {
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
