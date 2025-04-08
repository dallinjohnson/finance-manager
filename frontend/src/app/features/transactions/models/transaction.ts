export interface Transaction {
  date: Date;
  isCredit: boolean;
  amount: number;
  description: string | null;
  accountId: number | null;
  categoryId: number | null;
}
