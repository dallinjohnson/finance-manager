import { Account } from '../../../features/accounts/models/account';
import { Category } from '../../../features/categories/models/category';

export interface Transaction {
  id: number;
  title: string;
  date: string;        // YYYY-MM-DD
  credit: boolean;     // true if credit, false if debit
  amount: number;
  description: string | null;
  category: Category | null
  account: Account | null;
}