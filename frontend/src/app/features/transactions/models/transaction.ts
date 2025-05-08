import { Account } from '../../../features/accounts/models/account';
import { Category } from '../../../features/categories/models/category';

export interface Transaction {
  id: number;
  title: string;
  date: string;        // YYYY-MM-DD
  credit: boolean;     // true if credit, false if debit
  amount: number;
  description: string | undefined;
  category: Category | undefined
  account: Account | undefined;
}

export interface TransactionDTO {
  title: string;
  date: string;        // YYYY-MM-DD
  credit: boolean;     // true if credit, false if debit
  amount: number;
  description: string | undefined;
  categoryId: number | undefined
  accountId: number | undefined;
}