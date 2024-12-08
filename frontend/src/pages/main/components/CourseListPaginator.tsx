import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

interface CourseListPaginatorProps {
  page: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export function CourseListPaginator({
  page,
  totalPages,
  onPageChange,
}: CourseListPaginatorProps) {
  const generateItems = () => {
    const items = [];
    for (let i = 1; i <= totalPages; i++) {
      // if (i === 1 || i === totalPages) {
      items.push(
        <PaginationItem key={i}>
          <PaginationLink
            href="#"
            isActive={i === page}
            onClick={() => onPageChange(i)}
          >
            {i}
          </PaginationLink>
        </PaginationItem>
      );
      // }
    }
    return items;
  };

  return (
    <Pagination>
      <PaginationContent>
        <PaginationItem>
          <PaginationPrevious
            href="#"
            onClick={() => page > 1 && onPageChange(page - 1)}
          />
        </PaginationItem>
        {generateItems()}
        <PaginationItem>
          <PaginationNext
            href="#"
            onClick={() => page < totalPages && onPageChange(page + 1)}
          />
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}
