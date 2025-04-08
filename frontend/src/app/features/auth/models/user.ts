export interface User {
  email: string;
  role: Role;
}

export enum Role {
  USER = 'USER',
  ADMIN = 'ADMIN',
}
